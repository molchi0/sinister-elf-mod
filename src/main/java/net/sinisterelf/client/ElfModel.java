package net.sinisterelf.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Горбатый зловещий эльф. ФАЙЛ СГЕНЕРИРОВАН из спецификации (геометрия), позы руки пишутся в setupAnim.
 * Текстура 128x64: костюм санта-эльфа, потрёпанный; красная улыбка; арбалет с огненной стрелой.
 */
public class ElfModel extends EntityModel<ElfRenderState> {
    /** Наклон торса вперёд: горб. */
    private static final float HUNCH = 0.5F;
    /** Голова опущена и выдвинута вперёд. */
    private static final float HEAD_DROOP = 0.3F;

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart crossbow;
    private final ModelPart hatTip1;
    private final ModelPart hatTip2;

    public ElfModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.crossbow = root.getChild("crossbow");
        this.hatTip1 = this.head.getChild("hat_tip1");
        this.hatTip2 = this.hatTip1.getChild("hat_tip2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F)
                        .texOffs(68, 32).addBox(-1.0F, -5.0F, -5.0F, 2.0F, 2.0F, 1.0F)
                        .texOffs(76, 32).addBox(-1.0F, -4.0F, -6.0F, 2.0F, 1.0F, 1.0F)
                        .texOffs(52, 32).addBox(4.0F, -5.0F, -0.5F, 3.0F, 2.0F, 1.0F)
                        .texOffs(60, 32).addBox(6.0F, -7.0F, -0.5F, 2.0F, 2.0F, 1.0F)
                        .texOffs(52, 32).addBox(-7.0F, -5.0F, -0.5F, 3.0F, 2.0F, 1.0F)
                        .texOffs(60, 32).addBox(-8.0F, -7.0F, -0.5F, 2.0F, 2.0F, 1.0F)
                        .texOffs(32, 0).addBox(-4.5F, -10.0F, -4.5F, 9.0F, 2.0F, 9.0F)
                        .texOffs(70, 0).addBox(-3.0F, -13.0F, -3.0F, 6.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 9.5F, -3.2F));
        PartDefinition hatTip1 = head.addOrReplaceChild("hat_tip1",
                CubeListBuilder.create()
                        .texOffs(96, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -12.5F, 0.0F, -0.55F, 0.0F, 0.0F));
        PartDefinition hatTip2 = hatTip1.addOrReplaceChild("hat_tip2",
                CubeListBuilder.create()
                        .texOffs(96, 10).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, -0.9F, 0.0F, 0.0F));
        PartDefinition bell = hatTip2.addOrReplaceChild("bell",
                CubeListBuilder.create()
                        .texOffs(112, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, -4.0F, 0.0F));
        PartDefinition body = root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 16).addBox(-4.0F, -8.0F, -2.0F, 8.0F, 8.0F, 4.0F)
                        .texOffs(24, 16).addBox(-3.0F, -9.5F, 1.5F, 6.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 16.5F, 0.0F, 0.5F, 0.0F, 0.0F));
        PartDefinition skirt = body.addOrReplaceChild("skirt",
                CubeListBuilder.create()
                        .texOffs(72, 16).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 3.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition rightArm = root.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(48, 16).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F),
                PartPose.offset(-5.5F, 10.0F, -3.5F));
        PartDefinition leftArm = root.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(60, 16).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F),
                PartPose.offset(5.5F, 10.0F, -3.5F));
        PartDefinition rightLeg = root.addOrReplaceChild("right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                PartPose.offset(-2.0F, 16.0F, 0.0F));
        PartDefinition rightBoot = rightLeg.addOrReplaceChild("right_boot",
                CubeListBuilder.create()
                        .texOffs(32, 32).addBox(-1.5F, 6.0F, -5.0F, 3.0F, 2.0F, 3.0F)
                        .texOffs(44, 32).addBox(-1.0F, 4.0F, -5.0F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition leftLeg = root.addOrReplaceChild("left_leg",
                CubeListBuilder.create()
                        .texOffs(16, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                PartPose.offset(2.0F, 16.0F, 0.0F));
        PartDefinition leftBoot = leftLeg.addOrReplaceChild("left_boot",
                CubeListBuilder.create()
                        .texOffs(32, 32).addBox(-1.5F, 6.0F, -5.0F, 3.0F, 2.0F, 3.0F)
                        .texOffs(44, 32).addBox(-1.0F, 4.0F, -5.0F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition crossbow = root.addOrReplaceChild("crossbow",
                CubeListBuilder.create()
                        .texOffs(0, 48).addBox(-0.5F, -1.0F, -8.0F, 1.0F, 2.0F, 8.0F)
                        .texOffs(20, 48).addBox(-5.0F, -0.5F, -8.5F, 10.0F, 1.0F, 1.0F)
                        .texOffs(44, 48).addBox(-5.0F, -0.5F, -6.5F, 10.0F, 1.0F, 1.0F)
                        .texOffs(66, 48).addBox(-0.5F, -2.0F, -9.5F, 1.0F, 1.0F, 6.0F)
                        .texOffs(82, 48).addBox(-1.0F, -2.5F, -10.5F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 10.0F, -3.5F));

        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(ElfRenderState state) {
        super.setupAnim(state);
        float walk = state.walkAnimationPos;
        float speed = state.walkAnimationSpeed;
        float pitch = state.xRot * Mth.DEG_TO_RAD;

        this.head.yRot = state.yRot * Mth.DEG_TO_RAD;
        this.head.xRot = pitch + HEAD_DROOP;
        this.body.xRot = HUNCH + Mth.sin(walk * 1.3324F) * 0.04F * speed;

        float swing = Mth.cos(walk * 0.6662F) * 1.2F * speed;
        this.rightLeg.xRot = swing;
        this.leftLeg.xRot = -swing;

        // Шапка качается: длинный обвисший кончик с бубенчиком.
        float sway = Mth.sin(state.ageInTicks * 0.09F);
        this.hatTip1.xRot = -0.55F + sway * 0.06F;
        this.hatTip2.xRot = -0.9F + sway * 0.1F;

        if (state.aggressive) {
            // Целится: арбалет на уровне плеч, обе руки вперёд, наклон вслед за взглядом.
            this.rightArm.xRot = -1.5F + pitch;
            this.rightArm.yRot = -0.55F;
            this.leftArm.xRot = -1.55F + pitch;
            this.leftArm.yRot = 0.55F;
            this.crossbow.xRot = pitch;
        } else {
            // Держит арбалет внизу перед собой.
            this.rightArm.xRot = -0.85F;
            this.rightArm.yRot = -0.35F;
            this.leftArm.xRot = -0.95F;
            this.leftArm.yRot = 0.35F;
            this.crossbow.xRot = 0.65F;
        }
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.crossbow.yRot = 0.0F;
    }
}
