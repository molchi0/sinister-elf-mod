package net.sinisterelf.entity;

import java.util.EnumSet;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

/**
 * Зловещий эльф: горбатый, с арбалетом. Дальний бой: заряжает арбалет и стреляет
 * огненными стрелами, как разбойник (pillager). Скорость ходьбы средняя.
 */
public class SinisterElf extends Monster {
    /** Скорость стрелы (у разбойника около 1.6–3). */
    public static final float ARROW_SPEED = 2.0F;
    /** Базовый урон стрелы: при скорости 2.0 это около 5 урона (2,5 сердца) плюс горение. */
    public static final double ARROW_BASE_DAMAGE = 2.5D;
    /** Сколько тиков горит стрела (и поджигает цель). 100 тиков = 5 секунд. */
    public static final int FIRE_TICKS = 100;
    /** Сколько тиков заряжается арбалет перед выстрелом. */
    public static final int CHARGE_TICKS = 25;
    /** Пауза после выстрела. */
    public static final int COOLDOWN_TICKS = 20;
    /** Дальность стрельбы и дистанция, ближе которой эльф пятится. */
    public static final float SHOOT_RANGE = 16.0F;
    public static final float KEEP_DISTANCE = 6.0F;

    public SinisterElf(EntityType<? extends SinisterElf> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                // Средняя скорость: быстрее зомби (0.23) и скелета (0.25), медленнее разбойника (0.35).
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.FOLLOW_RANGE, 28.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CrossbowShootGoal(this, 1.0D, SHOOT_RANGE, KEEP_DISTANCE));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    /** Выстрел горящей стрелой в цель. */
    void shootFireArrow(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        // var: не привязываемся к имени класса стрелы (в 26.x пакеты projectile перемешали).
        var arrow = ((ArrowItem) Items.ARROW).createArrow(serverLevel, new ItemStack(Items.ARROW), this, null);
        arrow.setBaseDamage(ARROW_BASE_DAMAGE);
        arrow.setRemainingFireTicks(FIRE_TICKS);

        double dx = target.getX() - this.getX();
        double dy = target.getY(0.3333333333333333D) - arrow.getY();
        double dz = target.getZ() - this.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        arrow.shoot(dx, dy + horizontal * 0.2D, dz, ARROW_SPEED, 4.0F);

        this.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        serverLevel.addFreshEntity(arrow);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    /**
     * Стрельба как у разбойника: подходит на дальность выстрела, останавливается,
     * заряжает арбалет (около секунды), стреляет, делает паузу; если цель слишком близко, пятится.
     */
    static class CrossbowShootGoal extends Goal {
        private final SinisterElf elf;
        private final double speed;
        private final double maxDistSq;
        private final double minDistSq;
        private int chargeTicks;
        private int cooldown;

        CrossbowShootGoal(SinisterElf elf, double speed, float maxDistance, float minDistance) {
            this.elf = elf;
            this.speed = speed;
            this.maxDistSq = (double) maxDistance * maxDistance;
            this.minDistSq = (double) minDistance * minDistance;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.elf.getTarget();
            return target != null && target.isAlive();
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void start() {
            this.elf.setAggressive(true);
            this.chargeTicks = 0;
            this.cooldown = COOLDOWN_TICKS;
        }

        @Override
        public void stop() {
            this.elf.setAggressive(false);
            this.elf.getNavigation().stop();
            this.chargeTicks = 0;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.elf.getTarget();
            if (target == null) {
                return;
            }
            double distSq = this.elf.distanceToSqr(target);
            boolean sees = this.elf.getSensing().hasLineOfSight(target);
            this.elf.getLookControl().setLookAt(target, 30.0F, 30.0F);

            if (!sees || distSq > this.maxDistSq) {
                this.elf.getNavigation().moveTo(target, this.speed);
            } else if (distSq < this.minDistSq) {
                this.elf.getNavigation().stop();
                this.elf.getMoveControl().strafe(-0.5F, 0.0F);
            } else {
                this.elf.getNavigation().stop();
            }

            if (this.cooldown > 0) {
                this.cooldown--;
                return;
            }
            if (sees && distSq <= this.maxDistSq) {
                if (this.chargeTicks == 0) {
                    // В 26.2 это Holder<SoundEvent>, поэтому .value().
                    this.elf.playSound(SoundEvents.CROSSBOW_LOADING_START.value(), 1.0F, 1.0F);
                }
                this.chargeTicks++;
                if (this.chargeTicks >= CHARGE_TICKS) {
                    this.elf.shootFireArrow(target);
                    this.chargeTicks = 0;
                    this.cooldown = COOLDOWN_TICKS;
                }
            } else {
                this.chargeTicks = 0;
            }
        }
    }
}
