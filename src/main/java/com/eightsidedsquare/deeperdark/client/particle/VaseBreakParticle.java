package com.eightsidedsquare.deeperdark.client.particle;

import com.eightsidedsquare.deeperdark.core.DeeperDarkParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;

import java.util.function.Consumer;

public class VaseBreakParticle extends SpriteBillboardParticle {

    SpriteProvider basespriteSet;
    private static final Vec3f field_38334 = (Vec3f) Util.make(new Vec3f(0.5F, 0.5F, 0.5F), Vec3f::normalize);
    private static final Vec3f field_38335 = new Vec3f(-1.0F, -1.0F, 0.0F);
    private static final float X_ROTATION = 1.0472F;

    protected VaseBreakParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
                                SpriteProvider spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.velocityMultiplier = 0F;
        this.x = xd;
        this.y = yd;
        this.z = zd;
      //  this.scale *= 0.6F;
       // this.field_3847 = 24;
        this.scale = 14.0F;
        this.maxAge = 25;
        this.setSpriteForAge(spriteSet);
        this.gravityStrength = 0.0F;

        this.velocityX = 0F;
        this.velocityY = 0F;
        this.velocityZ = 0F;
        this.velocityMultiplier = 0.0F;
        this.setVelocity(0,0,0);
        //this.getBrightness(100F);
        this.setPos(xCoord, yCoord, zCoord);

        //this.prevAngle = 1F;

        //this.angle = 1F;

        // this.onGround = true;


        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
        basespriteSet = spriteSet;


    }

    //Float Val = MathHelper.lerp((float)maxAge, 0F, 10F);
    @Override
    public void tick() {
        super.tick();


        //this.onGround = true;
        //this.getBrightness(100F);
        //this.angle = 0F;
        //this.prevAngle = 0F;

        //this.scale = Val; //((6/(float)maxAge) * age + 1);
        this.setSpriteForAge(basespriteSet);
        //fadeOut();


    }


    public float getSize(float tickDelta) {
        float d = ((float)this.age + tickDelta) / (float)this.maxAge;
        return this.scale * MathHelper.clamp(d, 0.0F, 1.0F);
    }


//    private void fadeOut() {
//        //this.alpha = (-(1/(float)maxAge) * age + 1);
//        float scale = MathHelper.lerp(0.1f, this.scale, ((6 / (float) maxAge) * age + 1));
//
////        this.scale = ((6 / (float) maxAge) * age + 1);
//        this.scale = scale;
//
//
//
//        //fadeOut();
//    }


    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {

        //this.alpha = 1.0F - MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge, 0.0F, 1.0F);
        this.buildGeometry(vertexConsumer, camera, tickDelta, (quaternion) -> {
            quaternion.hamiltonProduct(Vec3f.POSITIVE_Y.getRadialQuaternion(0.0F));
            quaternion.hamiltonProduct(Vec3f.POSITIVE_X.getRadialQuaternion(-1.56F));
        });
        this.buildGeometry(vertexConsumer, camera, tickDelta, (quaternion) -> {
            quaternion.hamiltonProduct(Vec3f.POSITIVE_Y.getRadialQuaternion(-3.1415927F));
            quaternion.hamiltonProduct(Vec3f.POSITIVE_X.getRadialQuaternion(1.56F));
        });

    }

    private void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta, Consumer<Quaternion> rotator) {
        Vec3d vec3d = camera.getPos();
        float f = (float) (MathHelper.lerp((double) tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float) (MathHelper.lerp((double) tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float) (MathHelper.lerp((double) tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        Quaternion quaternion = new Quaternion(field_38334, 0.0F, true);
        rotator.accept(quaternion);
        field_38335.rotate(quaternion);
        Vec3f[] vec3fs = new Vec3f[]{new Vec3f(-1.0F, -1.0F, 0.0F), new Vec3f(-1.0F, 1.0F, 0.0F), new Vec3f(1.0F, 1.0F, 0.0F), new Vec3f(1.0F, -1.0F, 0.0F)};
        float i = this.getSize(tickDelta);

        int j;
        for (j = 0; j < 4; ++j) {
            Vec3f vec3f = vec3fs[j];
            vec3f.rotate(quaternion);
            vec3f.scale(i);
            vec3f.add(f, g, h);
        }

        j = this.getBrightness(tickDelta);
        this.vertex(vertexConsumer, vec3fs[0], this.getMaxU(), this.getMaxV(), j);
        this.vertex(vertexConsumer, vec3fs[1], this.getMaxU(), this.getMinV(), j);
        this.vertex(vertexConsumer, vec3fs[2], this.getMinU(), this.getMinV(), j);
        this.vertex(vertexConsumer, vec3fs[3], this.getMinU(), this.getMaxV(), j);
    }

    private void vertex(VertexConsumer vertexConsumer, Vec3f pos, float u, float v, int light) {
        vertexConsumer.vertex((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()).texture(u, v).color(this.red, this.green, this.blue, this.alpha).light(light).next();
    }


    public int getBrightness(float tint) {
        return 240;
    }

    @Override
    public ParticleTextureSheet getType() {

        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new VaseBreakParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }

    public static void spawnParticle(BlockPos pos, Vec3d velocity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            client.particleManager.addParticle(DeeperDarkParticles.VASE_BREAK_PARTICLE, pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5, velocity.getX(), velocity.getY(), velocity.getZ());
        }
    }

    public static void spawnParticle2(BlockPos pos, Vec3d velocity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            client.particleManager.addParticle(DeeperDarkParticles.VASE_BREAK_PARTICLE, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, velocity.getX(), velocity.getY(), velocity.getZ());
        }
    }


    public static void spawnParticle3(BlockPos pos, Vec3d velocity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            client.particleManager.addParticle(DeeperDarkParticles.VASE_BREAK_PARTICLE, pos.getX() + 0.5, pos.getY() + 2.5, pos.getZ() + 0.5, velocity.getX(), velocity.getY(), velocity.getZ());
        }
    }

    public static void spawnParticle4(BlockPos pos, Vec3d velocity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            client.particleManager.addParticle(DeeperDarkParticles.VASE_BREAK_PARTICLE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, velocity.getX(), velocity.getY(), velocity.getZ());
        }
    }


}