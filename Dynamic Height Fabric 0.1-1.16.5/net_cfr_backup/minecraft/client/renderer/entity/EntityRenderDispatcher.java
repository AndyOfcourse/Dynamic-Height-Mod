/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Matrix4f
 *  com.mojang.math.Quaternion
 *  com.mojang.math.Vector3f
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.CrashReport
 *  net.minecraft.CrashReportCategory
 *  net.minecraft.ReportedException
 *  net.minecraft.client.Camera
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.Options
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.Sheets
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.entity.AreaEffectCloudRenderer
 *  net.minecraft.client.renderer.entity.ArmorStandRenderer
 *  net.minecraft.client.renderer.entity.BatRenderer
 *  net.minecraft.client.renderer.entity.BeeRenderer
 *  net.minecraft.client.renderer.entity.BlazeRenderer
 *  net.minecraft.client.renderer.entity.BoatRenderer
 *  net.minecraft.client.renderer.entity.CatRenderer
 *  net.minecraft.client.renderer.entity.CaveSpiderRenderer
 *  net.minecraft.client.renderer.entity.ChestedHorseRenderer
 *  net.minecraft.client.renderer.entity.ChickenRenderer
 *  net.minecraft.client.renderer.entity.CodRenderer
 *  net.minecraft.client.renderer.entity.CowRenderer
 *  net.minecraft.client.renderer.entity.CreeperRenderer
 *  net.minecraft.client.renderer.entity.DolphinRenderer
 *  net.minecraft.client.renderer.entity.DragonFireballRenderer
 *  net.minecraft.client.renderer.entity.DrownedRenderer
 *  net.minecraft.client.renderer.entity.ElderGuardianRenderer
 *  net.minecraft.client.renderer.entity.EndCrystalRenderer
 *  net.minecraft.client.renderer.entity.EnderDragonRenderer
 *  net.minecraft.client.renderer.entity.EndermanRenderer
 *  net.minecraft.client.renderer.entity.EndermiteRenderer
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EvokerFangsRenderer
 *  net.minecraft.client.renderer.entity.EvokerRenderer
 *  net.minecraft.client.renderer.entity.ExperienceOrbRenderer
 *  net.minecraft.client.renderer.entity.FallingBlockRenderer
 *  net.minecraft.client.renderer.entity.FireworkEntityRenderer
 *  net.minecraft.client.renderer.entity.FishingHookRenderer
 *  net.minecraft.client.renderer.entity.FoxRenderer
 *  net.minecraft.client.renderer.entity.GhastRenderer
 *  net.minecraft.client.renderer.entity.GiantMobRenderer
 *  net.minecraft.client.renderer.entity.GuardianRenderer
 *  net.minecraft.client.renderer.entity.HoglinRenderer
 *  net.minecraft.client.renderer.entity.HorseRenderer
 *  net.minecraft.client.renderer.entity.HuskRenderer
 *  net.minecraft.client.renderer.entity.IllusionerRenderer
 *  net.minecraft.client.renderer.entity.IronGolemRenderer
 *  net.minecraft.client.renderer.entity.ItemEntityRenderer
 *  net.minecraft.client.renderer.entity.ItemFrameRenderer
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.client.renderer.entity.LeashKnotRenderer
 *  net.minecraft.client.renderer.entity.LightningBoltRenderer
 *  net.minecraft.client.renderer.entity.LlamaRenderer
 *  net.minecraft.client.renderer.entity.LlamaSpitRenderer
 *  net.minecraft.client.renderer.entity.MagmaCubeRenderer
 *  net.minecraft.client.renderer.entity.MinecartRenderer
 *  net.minecraft.client.renderer.entity.MushroomCowRenderer
 *  net.minecraft.client.renderer.entity.OcelotRenderer
 *  net.minecraft.client.renderer.entity.PaintingRenderer
 *  net.minecraft.client.renderer.entity.PandaRenderer
 *  net.minecraft.client.renderer.entity.ParrotRenderer
 *  net.minecraft.client.renderer.entity.PhantomRenderer
 *  net.minecraft.client.renderer.entity.PigRenderer
 *  net.minecraft.client.renderer.entity.PiglinRenderer
 *  net.minecraft.client.renderer.entity.PillagerRenderer
 *  net.minecraft.client.renderer.entity.PolarBearRenderer
 *  net.minecraft.client.renderer.entity.PufferfishRenderer
 *  net.minecraft.client.renderer.entity.RabbitRenderer
 *  net.minecraft.client.renderer.entity.RavagerRenderer
 *  net.minecraft.client.renderer.entity.SalmonRenderer
 *  net.minecraft.client.renderer.entity.SheepRenderer
 *  net.minecraft.client.renderer.entity.ShulkerBulletRenderer
 *  net.minecraft.client.renderer.entity.ShulkerRenderer
 *  net.minecraft.client.renderer.entity.SilverfishRenderer
 *  net.minecraft.client.renderer.entity.SkeletonRenderer
 *  net.minecraft.client.renderer.entity.SlimeRenderer
 *  net.minecraft.client.renderer.entity.SnowGolemRenderer
 *  net.minecraft.client.renderer.entity.SpectralArrowRenderer
 *  net.minecraft.client.renderer.entity.SpiderRenderer
 *  net.minecraft.client.renderer.entity.SquidRenderer
 *  net.minecraft.client.renderer.entity.StrayRenderer
 *  net.minecraft.client.renderer.entity.StriderRenderer
 *  net.minecraft.client.renderer.entity.ThrownItemRenderer
 *  net.minecraft.client.renderer.entity.ThrownTridentRenderer
 *  net.minecraft.client.renderer.entity.TippableArrowRenderer
 *  net.minecraft.client.renderer.entity.TntMinecartRenderer
 *  net.minecraft.client.renderer.entity.TntRenderer
 *  net.minecraft.client.renderer.entity.TropicalFishRenderer
 *  net.minecraft.client.renderer.entity.TurtleRenderer
 *  net.minecraft.client.renderer.entity.UndeadHorseRenderer
 *  net.minecraft.client.renderer.entity.VexRenderer
 *  net.minecraft.client.renderer.entity.VillagerRenderer
 *  net.minecraft.client.renderer.entity.VindicatorRenderer
 *  net.minecraft.client.renderer.entity.WanderingTraderRenderer
 *  net.minecraft.client.renderer.entity.WitchRenderer
 *  net.minecraft.client.renderer.entity.WitherBossRenderer
 *  net.minecraft.client.renderer.entity.WitherSkeletonRenderer
 *  net.minecraft.client.renderer.entity.WitherSkullRenderer
 *  net.minecraft.client.renderer.entity.WolfRenderer
 *  net.minecraft.client.renderer.entity.ZoglinRenderer
 *  net.minecraft.client.renderer.entity.ZombieRenderer
 *  net.minecraft.client.renderer.entity.ZombieVillagerRenderer
 *  net.minecraft.client.renderer.entity.player.PlayerRenderer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.resources.model.ModelBakery
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ReloadableResourceManager
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.boss.EnderDragonPart
 *  net.minecraft.world.entity.boss.enderdragon.EnderDragon
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.Nullable
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.AreaEffectCloudRenderer;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.BatRenderer;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.CaveSpiderRenderer;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EndermiteRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.client.renderer.entity.EvokerRenderer;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.FireworkEntityRenderer;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.client.renderer.entity.GiantMobRenderer;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.client.renderer.entity.HoglinRenderer;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.client.renderer.entity.IronGolemRenderer;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LeashKnotRenderer;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.client.renderer.entity.MagmaCubeRenderer;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.entity.MushroomCowRenderer;
import net.minecraft.client.renderer.entity.OcelotRenderer;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.entity.PandaRenderer;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.PhantomRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.RavagerRenderer;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.ShulkerBulletRenderer;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.SilverfishRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.client.renderer.entity.SpectralArrowRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.client.renderer.entity.TurtleRenderer;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.client.renderer.entity.WanderingTraderRenderer;
import net.minecraft.client.renderer.entity.WitchRenderer;
import net.minecraft.client.renderer.entity.WitherBossRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.ZoglinRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public class EntityRenderDispatcher {
    private static final RenderType SHADOW_RENDER_TYPE = RenderType.entityShadow((ResourceLocation)new ResourceLocation("textures/misc/shadow.png"));
    private final Map<EntityType<?>, EntityRenderer<?>> renderers = Maps.newHashMap();
    private final Map<String, PlayerRenderer> playerRenderers = Maps.newHashMap();
    private final PlayerRenderer defaultPlayerRenderer;
    private final Font font;
    public final TextureManager textureManager;
    private Level level;
    public Camera camera;
    private Quaternion cameraOrientation;
    public Entity crosshairPickEntity;
    public final Options options;
    private boolean shouldRenderShadow = true;
    private boolean renderHitBoxes;

    public <E extends Entity> int getPackedLightCoords(E entity, float f) {
        return this.getRenderer(entity).getPackedLightCoords(entity, f);
    }

    private <T extends Entity> void register(EntityType<T> entityType, EntityRenderer<? super T> entityRenderer) {
        this.renderers.put(entityType, entityRenderer);
    }

    private void registerRenderers(ItemRenderer itemRenderer, ReloadableResourceManager reloadableResourceManager) {
        this.register((EntityType)EntityType.AREA_EFFECT_CLOUD, (EntityRenderer)new AreaEffectCloudRenderer(this));
        this.register((EntityType)EntityType.ARMOR_STAND, (EntityRenderer)new ArmorStandRenderer(this));
        this.register((EntityType)EntityType.ARROW, (EntityRenderer)new TippableArrowRenderer(this));
        this.register((EntityType)EntityType.BAT, (EntityRenderer)new BatRenderer(this));
        this.register((EntityType)EntityType.BEE, (EntityRenderer)new BeeRenderer(this));
        this.register((EntityType)EntityType.BLAZE, (EntityRenderer)new BlazeRenderer(this));
        this.register((EntityType)EntityType.BOAT, (EntityRenderer)new BoatRenderer(this));
        this.register((EntityType)EntityType.CAT, (EntityRenderer)new CatRenderer(this));
        this.register((EntityType)EntityType.CAVE_SPIDER, (EntityRenderer)new CaveSpiderRenderer(this));
        this.register((EntityType)EntityType.CHEST_MINECART, (EntityRenderer)new MinecartRenderer(this));
        this.register((EntityType)EntityType.CHICKEN, (EntityRenderer)new ChickenRenderer(this));
        this.register((EntityType)EntityType.COD, (EntityRenderer)new CodRenderer(this));
        this.register((EntityType)EntityType.COMMAND_BLOCK_MINECART, (EntityRenderer)new MinecartRenderer(this));
        this.register((EntityType)EntityType.COW, (EntityRenderer)new CowRenderer(this));
        this.register((EntityType)EntityType.CREEPER, (EntityRenderer)new CreeperRenderer(this));
        this.register((EntityType)EntityType.DOLPHIN, (EntityRenderer)new DolphinRenderer(this));
        this.register((EntityType)EntityType.DONKEY, (EntityRenderer)new ChestedHorseRenderer(this, 0.87f));
        this.register((EntityType)EntityType.DRAGON_FIREBALL, (EntityRenderer)new DragonFireballRenderer(this));
        this.register((EntityType)EntityType.DROWNED, (EntityRenderer)new DrownedRenderer(this));
        this.register((EntityType)EntityType.EGG, (EntityRenderer)new ThrownItemRenderer(this, itemRenderer));
        this.register((EntityType)EntityType.ELDER_GUARDIAN, (EntityRenderer)new ElderGuardianRenderer(this));
        this.register((EntityType)EntityType.END_CRYSTAL, (EntityRenderer)new EndCrystalRenderer(this));
        this.register((EntityType)EntityType.ENDER_DRAGON, (EntityRenderer)new EnderDragonRenderer(this));
        this.register((EntityType)EntityType.ENDERMAN, (EntityRenderer)new EndermanRenderer(this));
        this.register((EntityType)EntityType.ENDERMITE, (EntityRenderer)new EndermiteRenderer(this));
        this.register((EntityType)EntityType.ENDER_PEARL, (EntityRenderer)new ThrownItemRenderer(this, itemRenderer));
        this.register((EntityType)EntityType.EVOKER_FANGS, (EntityRenderer)new EvokerFangsRenderer(this));
        this.register((EntityType)EntityType.EVOKER, (EntityRenderer)new EvokerRenderer(this));
        this.register((EntityType)EntityType.EXPERIENCE_BOTTLE, (EntityRenderer)new ThrownItemRenderer(this, itemRenderer));
        this.register((EntityType)EntityType.EXPERIENCE_ORB, (EntityRenderer)new ExperienceOrbRenderer(this));
        this.register((EntityType)EntityType.EYE_OF_ENDER, (EntityRenderer)new ThrownItemRenderer(this, itemRenderer, 1.0f, true));
        this.register((EntityType)EntityType.FALLING_BLOCK, (EntityRenderer)new FallingBlockRenderer(this));
        this.register((EntityType)EntityType.FIREBALL, (EntityRenderer)new ThrownItemRenderer(this, itemRenderer, 3.0f, true));
        this.register((EntityType)EntityType.FIREWORK_ROCKET, (EntityRenderer)new FireworkEntityRenderer(this, itemRenderer));
        this.register((EntityType)EntityType.FISHING_BOBBER, (EntityRenderer)new FishingHookRenderer(this));
        this.register((EntityType)EntityType.FOX, (EntityRenderer)new FoxRenderer(this));
        this.register((EntityType)EntityType.FURNACE_MINECART, (EntityRenderer)new MinecartRenderer(this));
        this.register((EntityType)EntityType.GHAST, (EntityRenderer)new GhastRenderer(this));
        this.register((EntityType)EntityType.GIANT, (EntityRenderer)new GiantMobRenderer(this, 6.0f));
        this.register((EntityType)EntityType.GUARDIAN, (EntityRenderer)new GuardianRenderer(this));
        this.register((EntityType)EntityType.HOGLIN, (EntityRenderer)new HoglinRenderer(this));
        this.register((EntityType)EntityType.HOPPER_MINECART, (EntityRenderer)new MinecartRenderer(this));
        this.register((EntityType)EntityType.HORSE, (EntityRenderer)new HorseRenderer(this));
        this.register((EntityType)EntityType.HUSK, (EntityRenderer)new HuskRenderer(this));
        this.register((EntityType)EntityType.ILLUSIONER, (EntityRenderer)new IllusionerRenderer(this));
        this.register((EntityType)EntityType.IRON_GOLEM, (EntityRenderer)new IronGolemRenderer(this));
        this.register((EntityType)EntityType.ITEM, (EntityRenderer)new ItemEntityRenderer(this, itemRenderer));
        this.register((EntityType)EntityType.ITEM_FRAME, (EntityRenderer)new ItemFrameRenderer(this, itemRenderer));
        this.register((EntityType)EntityType.LEASH_KNOT, (EntityRenderer)new LeashKnotRenderer(this));
        this.register((EntityType)EntityType.LIGHTNING_BOLT, (EntityRenderer)new LightningBoltRenderer(this));
        this.register((EntityType)EntityType.LLAMA, (EntityRenderer)new LlamaRenderer(this));
        this.register((EntityType)EntityType.LLAMA_SPIT, (EntityRenderer)new LlamaSpitRenderer(this));
        this.register((EntityType)EntityType.MAGMA_CUBE, (EntityRenderer)new MagmaCubeRenderer(this));
        this.register((EntityType)EntityType.MINECART, (EntityRenderer)new MinecartRenderer(this));
        this.register((EntityType)EntityType.MOOSHROOM, (EntityRenderer)new MushroomCowRenderer(this));
        this.register((EntityType)EntityType.MULE, (EntityRenderer)new ChestedHorseRenderer(this, 0.92f));
        this.register((EntityType)EntityType.OCELOT, (EntityRenderer)new OcelotRenderer(this));
        this.register((EntityType)EntityType.PAINTING, (EntityRenderer)new PaintingRenderer(this));
        this.register((EntityType)EntityType.PANDA, (EntityRenderer)new PandaRenderer(this));
        this.register((EntityType)EntityType.PARROT, (EntityRenderer)new ParrotRenderer(this));
        this.register((EntityType)EntityType.PHANTOM, (EntityRenderer)new PhantomRenderer(this));
        this.register((EntityType)EntityType.PIG, (EntityRenderer)new PigRenderer(this));
        this.register((EntityType)EntityType.PIGLIN, (EntityRenderer)new PiglinRenderer(this, false));
        this.register((EntityType)EntityType.PIGLIN_BRUTE, (EntityRenderer)new PiglinRenderer(this, false));
        this.register((EntityType)EntityType.PILLAGER, (EntityRenderer)new PillagerRenderer(this));
        this.register((EntityType)EntityType.POLAR_BEAR, (EntityRenderer)new PolarBearRenderer(this));
        this.register((EntityType)EntityType.POTION, (EntityRenderer)new ThrownItemRenderer(this, itemRenderer));
        this.register((EntityType)EntityType.PUFFERFISH, (EntityRenderer)new PufferfishRenderer(this));
        this.register((EntityType)EntityType.RABBIT, (EntityRenderer)new RabbitRenderer(this));
        this.register((EntityType)EntityType.RAVAGER, (EntityRenderer)new RavagerRenderer(this));
        this.register((EntityType)EntityType.SALMON, (EntityRenderer)new SalmonRenderer(this));
        this.register((EntityType)EntityType.SHEEP, (EntityRenderer)new SheepRenderer(this));
        this.register((EntityType)EntityType.SHULKER_BULLET, (EntityRenderer)new ShulkerBulletRenderer(this));
        this.register((EntityType)EntityType.SHULKER, (EntityRenderer)new ShulkerRenderer(this));
        this.register((EntityType)EntityType.SILVERFISH, (EntityRenderer)new SilverfishRenderer(this));
        this.register((EntityType)EntityType.SKELETON_HORSE, (EntityRenderer)new UndeadHorseRenderer(this));
        this.register((EntityType)EntityType.SKELETON, (EntityRenderer)new SkeletonRenderer(this));
        this.register((EntityType)EntityType.SLIME, (EntityRenderer)new SlimeRenderer(this));
        this.register((EntityType)EntityType.SMALL_FIREBALL, (EntityRenderer)new ThrownItemRenderer(this, itemRenderer, 0.75f, true));
        this.register((EntityType)EntityType.SNOWBALL, (EntityRenderer)new ThrownItemRenderer(this, itemRenderer));
        this.register((EntityType)EntityType.SNOW_GOLEM, (EntityRenderer)new SnowGolemRenderer(this));
        this.register((EntityType)EntityType.SPAWNER_MINECART, (EntityRenderer)new MinecartRenderer(this));
        this.register((EntityType)EntityType.SPECTRAL_ARROW, (EntityRenderer)new SpectralArrowRenderer(this));
        this.register((EntityType)EntityType.SPIDER, (EntityRenderer)new SpiderRenderer(this));
        this.register((EntityType)EntityType.SQUID, (EntityRenderer)new SquidRenderer(this));
        this.register((EntityType)EntityType.STRAY, (EntityRenderer)new StrayRenderer(this));
        this.register((EntityType)EntityType.TNT_MINECART, (EntityRenderer)new TntMinecartRenderer(this));
        this.register((EntityType)EntityType.TNT, (EntityRenderer)new TntRenderer(this));
        this.register((EntityType)EntityType.TRADER_LLAMA, (EntityRenderer)new LlamaRenderer(this));
        this.register((EntityType)EntityType.TRIDENT, (EntityRenderer)new ThrownTridentRenderer(this));
        this.register((EntityType)EntityType.TROPICAL_FISH, (EntityRenderer)new TropicalFishRenderer(this));
        this.register((EntityType)EntityType.TURTLE, (EntityRenderer)new TurtleRenderer(this));
        this.register((EntityType)EntityType.VEX, (EntityRenderer)new VexRenderer(this));
        this.register((EntityType)EntityType.VILLAGER, (EntityRenderer)new VillagerRenderer(this, reloadableResourceManager));
        this.register((EntityType)EntityType.VINDICATOR, (EntityRenderer)new VindicatorRenderer(this));
        this.register((EntityType)EntityType.WANDERING_TRADER, (EntityRenderer)new WanderingTraderRenderer(this));
        this.register((EntityType)EntityType.WITCH, (EntityRenderer)new WitchRenderer(this));
        this.register((EntityType)EntityType.WITHER, (EntityRenderer)new WitherBossRenderer(this));
        this.register((EntityType)EntityType.WITHER_SKELETON, (EntityRenderer)new WitherSkeletonRenderer(this));
        this.register((EntityType)EntityType.WITHER_SKULL, (EntityRenderer)new WitherSkullRenderer(this));
        this.register((EntityType)EntityType.WOLF, (EntityRenderer)new WolfRenderer(this));
        this.register((EntityType)EntityType.ZOGLIN, (EntityRenderer)new ZoglinRenderer(this));
        this.register((EntityType)EntityType.ZOMBIE_HORSE, (EntityRenderer)new UndeadHorseRenderer(this));
        this.register((EntityType)EntityType.ZOMBIE, (EntityRenderer)new ZombieRenderer(this));
        this.register((EntityType)EntityType.ZOMBIFIED_PIGLIN, (EntityRenderer)new PiglinRenderer(this, true));
        this.register((EntityType)EntityType.ZOMBIE_VILLAGER, (EntityRenderer)new ZombieVillagerRenderer(this, reloadableResourceManager));
        this.register((EntityType)EntityType.STRIDER, (EntityRenderer)new StriderRenderer(this));
    }

    public EntityRenderDispatcher(TextureManager textureManager, ItemRenderer itemRenderer, ReloadableResourceManager reloadableResourceManager, Font font, Options options) {
        this.textureManager = textureManager;
        this.font = font;
        this.options = options;
        this.registerRenderers(itemRenderer, reloadableResourceManager);
        this.defaultPlayerRenderer = new PlayerRenderer(this);
        this.playerRenderers.put("default", this.defaultPlayerRenderer);
        this.playerRenderers.put("slim", new PlayerRenderer(this, true));
        for (EntityType entityType : Registry.ENTITY_TYPE) {
            if (entityType == EntityType.PLAYER || this.renderers.containsKey(entityType)) continue;
            throw new IllegalStateException("No renderer registered for " + Registry.ENTITY_TYPE.getKey((Object)entityType));
        }
    }

    public <T extends Entity> EntityRenderer<? super T> getRenderer(T entity) {
        if (entity instanceof AbstractClientPlayer) {
            String string = ((AbstractClientPlayer)entity).getModelName();
            PlayerRenderer playerRenderer = this.playerRenderers.get(string);
            if (playerRenderer != null) {
                return playerRenderer;
            }
            return this.defaultPlayerRenderer;
        }
        return this.renderers.get(entity.getType());
    }

    public void prepare(Level level, Camera camera, Entity entity) {
        this.level = level;
        this.camera = camera;
        this.cameraOrientation = camera.rotation();
        this.crosshairPickEntity = entity;
    }

    public void overrideCameraOrientation(Quaternion quaternion) {
        this.cameraOrientation = quaternion;
    }

    public void setRenderShadow(boolean bl) {
        this.shouldRenderShadow = bl;
    }

    public void setRenderHitBoxes(boolean bl) {
        this.renderHitBoxes = bl;
    }

    public boolean shouldRenderHitBoxes() {
        return this.renderHitBoxes;
    }

    public <E extends Entity> boolean shouldRender(E entity, Frustum frustum, double d, double e, double f) {
        EntityRenderer<E> entityRenderer = this.getRenderer(entity);
        return entityRenderer.shouldRender(entity, frustum, d, e, f);
    }

    public <E extends Entity> void render(E entity, double d, double e, double f, float g, float h, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        EntityRenderer<E> entityRenderer = this.getRenderer(entity);
        try {
            double m;
            float n;
            Vec3 vec3 = entityRenderer.getRenderOffset(entity, h);
            double j = d + vec3.x();
            double k = e + vec3.y();
            double l = f + vec3.z();
            poseStack.pushPose();
            poseStack.translate(j, k, l);
            entityRenderer.render(entity, g, h, poseStack, multiBufferSource, i);
            if (entity.displayFireAnimation()) {
                this.renderFlame(poseStack, multiBufferSource, entity);
            }
            poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
            if (this.options.entityShadows && this.shouldRenderShadow && entityRenderer.shadowRadius > 0.0f && !entity.isInvisible() && (n = (float)((1.0 - (m = this.distanceToSqr(entity.getX(), entity.getY(), entity.getZ())) / 256.0) * (double)entityRenderer.shadowStrength)) > 0.0f) {
                EntityRenderDispatcher.renderShadow(poseStack, multiBufferSource, entity, n, h, (LevelReader)this.level, entityRenderer.shadowRadius);
            }
            if (this.renderHitBoxes && !entity.isInvisible() && !Minecraft.getInstance().showOnlyReducedInfo()) {
                this.renderHitbox(poseStack, multiBufferSource.getBuffer(RenderType.lines()), entity, h);
            }
            poseStack.popPose();
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.forThrowable((Throwable)throwable, (String)"Rendering entity in world");
            CrashReportCategory crashReportCategory = crashReport.addCategory("Entity being rendered");
            entity.fillCrashReportCategory(crashReportCategory);
            CrashReportCategory crashReportCategory2 = crashReport.addCategory("Renderer details");
            crashReportCategory2.setDetail("Assigned renderer", entityRenderer);
            crashReportCategory2.setDetail("Location", (Object)CrashReportCategory.formatLocation((double)d, (double)e, (double)f));
            crashReportCategory2.setDetail("Rotation", (Object)Float.valueOf(g));
            crashReportCategory2.setDetail("Delta", (Object)Float.valueOf(h));
            throw new ReportedException(crashReport);
        }
    }

    private void renderHitbox(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, float f) {
        float g = entity.getBbWidth() / 2.0f;
        this.renderBox(poseStack, vertexConsumer, entity, 1.0f, 1.0f, 1.0f);
        if (entity instanceof EnderDragon) {
            double d = -Mth.lerp((double)f, (double)entity.xOld, (double)entity.getX());
            double e = -Mth.lerp((double)f, (double)entity.yOld, (double)entity.getY());
            double h = -Mth.lerp((double)f, (double)entity.zOld, (double)entity.getZ());
            for (EnderDragonPart enderDragonPart : ((EnderDragon)entity).getSubEntities()) {
                poseStack.pushPose();
                double i = d + Mth.lerp((double)f, (double)enderDragonPart.xOld, (double)enderDragonPart.getX());
                double j = e + Mth.lerp((double)f, (double)enderDragonPart.yOld, (double)enderDragonPart.getY());
                double k = h + Mth.lerp((double)f, (double)enderDragonPart.zOld, (double)enderDragonPart.getZ());
                poseStack.translate(i, j, k);
                this.renderBox(poseStack, vertexConsumer, (Entity)enderDragonPart, 0.25f, 1.0f, 0.0f);
                poseStack.popPose();
            }
        }
        if (entity instanceof LivingEntity) {
            float l = 0.01f;
            LevelRenderer.renderLineBox((PoseStack)poseStack, (VertexConsumer)vertexConsumer, (double)(-g), (double)(entity.getEyeHeight() - 0.01f), (double)(-g), (double)g, (double)(entity.getEyeHeight() + 0.01f), (double)g, (float)1.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        }
        Vec3 vec3 = entity.getViewVector(f);
        Matrix4f matrix4f = poseStack.last().pose();
        vertexConsumer.vertex(matrix4f, 0.0f, entity.getEyeHeight(), 0.0f).color(0, 0, 255, 255).endVertex();
        vertexConsumer.vertex(matrix4f, (float)(vec3.x * 2.0), (float)((double)entity.getEyeHeight() + vec3.y * 2.0), (float)(vec3.z * 2.0)).color(0, 0, 255, 255).endVertex();
    }

    private void renderBox(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, float f, float g, float h) {
        AABB aABB = entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ());
        LevelRenderer.renderLineBox((PoseStack)poseStack, (VertexConsumer)vertexConsumer, (AABB)aABB, (float)f, (float)g, (float)h, (float)1.0f);
    }

    private void renderFlame(PoseStack poseStack, MultiBufferSource multiBufferSource, Entity entity) {
        TextureAtlasSprite textureAtlasSprite = ModelBakery.FIRE_0.sprite();
        TextureAtlasSprite textureAtlasSprite2 = ModelBakery.FIRE_1.sprite();
        poseStack.pushPose();
        float f = entity.getBbWidth() * 1.4f;
        poseStack.scale(f, f, f);
        float g = 0.5f;
        float h = 0.0f;
        float i = entity.getBbHeight() / f;
        float j = 0.0f;
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.camera.getYRot()));
        poseStack.translate(0.0, 0.0, (double)(-0.3f + (float)((int)i) * 0.02f));
        float k = 0.0f;
        int l = 0;
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());
        PoseStack.Pose pose = poseStack.last();
        while (i > 0.0f) {
            TextureAtlasSprite textureAtlasSprite3 = l % 2 == 0 ? textureAtlasSprite : textureAtlasSprite2;
            float m = textureAtlasSprite3.getU0();
            float n = textureAtlasSprite3.getV0();
            float o = textureAtlasSprite3.getU1();
            float p = textureAtlasSprite3.getV1();
            if (l / 2 % 2 == 0) {
                float q = o;
                o = m;
                m = q;
            }
            EntityRenderDispatcher.fireVertex(pose, vertexConsumer, g - 0.0f, 0.0f - j, k, o, p);
            EntityRenderDispatcher.fireVertex(pose, vertexConsumer, -g - 0.0f, 0.0f - j, k, m, p);
            EntityRenderDispatcher.fireVertex(pose, vertexConsumer, -g - 0.0f, 1.4f - j, k, m, n);
            EntityRenderDispatcher.fireVertex(pose, vertexConsumer, g - 0.0f, 1.4f - j, k, o, n);
            i -= 0.45f;
            j -= 0.45f;
            g *= 0.9f;
            k += 0.03f;
            ++l;
        }
        poseStack.popPose();
    }

    private static void fireVertex(PoseStack.Pose pose, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j) {
        vertexConsumer.vertex(pose.pose(), f, g, h).color(255, 255, 255, 255).uv(i, j).overlayCoords(0, 10).uv2(240).normal(pose.normal(), 0.0f, 1.0f, 0.0f).endVertex();
    }

    private static void renderShadow(PoseStack poseStack, MultiBufferSource multiBufferSource, Entity entity, float f, float g, LevelReader levelReader, float h) {
        Mob mob;
        float i = h;
        if (entity instanceof Mob && (mob = (Mob)entity).isBaby()) {
            i *= 0.5f;
        }
        double d = Mth.lerp((double)g, (double)entity.xOld, (double)entity.getX());
        double e = Mth.lerp((double)g, (double)entity.yOld, (double)entity.getY());
        double j = Mth.lerp((double)g, (double)entity.zOld, (double)entity.getZ());
        int k = Mth.floor((double)(d - (double)i));
        int l = Mth.floor((double)(d + (double)i));
        int m = Mth.floor((double)(e - (double)i));
        int n = Mth.floor((double)e);
        int o = Mth.floor((double)(j - (double)i));
        int p = Mth.floor((double)(j + (double)i));
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(SHADOW_RENDER_TYPE);
        for (BlockPos blockPos : BlockPos.betweenClosed((BlockPos)new BlockPos(k, m, o), (BlockPos)new BlockPos(l, n, p))) {
            EntityRenderDispatcher.renderBlockShadow(pose, vertexConsumer, levelReader, blockPos, d, e, j, i, f);
        }
    }

    private static void renderBlockShadow(PoseStack.Pose pose, VertexConsumer vertexConsumer, LevelReader levelReader, BlockPos blockPos, double d, double e, double f, float g, float h) {
        BlockPos blockPos2 = blockPos.below();
        BlockState blockState = levelReader.getBlockState(blockPos2);
        if (blockState.getRenderShape() == RenderShape.INVISIBLE || levelReader.getMaxLocalRawBrightness(blockPos) <= 3) {
            return;
        }
        if (!blockState.isCollisionShapeFullBlock((BlockGetter)levelReader, blockPos2)) {
            return;
        }
        VoxelShape voxelShape = blockState.getShape((BlockGetter)levelReader, blockPos.below());
        if (voxelShape.isEmpty()) {
            return;
        }
        float i = (float)(((double)h - (e - (double)blockPos.getY()) / 2.0) * 0.5 * (double)levelReader.getBrightness(blockPos));
        if (i >= 0.0f) {
            if (i > 1.0f) {
                i = 1.0f;
            }
            AABB aABB = voxelShape.bounds();
            double j = (double)blockPos.getX() + aABB.minX;
            double k = (double)blockPos.getX() + aABB.maxX;
            double l = (double)blockPos.getY() + aABB.minY;
            double m = (double)blockPos.getZ() + aABB.minZ;
            double n = (double)blockPos.getZ() + aABB.maxZ;
            float o = (float)(j - d);
            float p = (float)(k - d);
            float q = (float)(l - e);
            float r = (float)(m - f);
            float s = (float)(n - f);
            float t = -o / 2.0f / g + 0.5f;
            float u = -p / 2.0f / g + 0.5f;
            float v = -r / 2.0f / g + 0.5f;
            float w = -s / 2.0f / g + 0.5f;
            EntityRenderDispatcher.shadowVertex(pose, vertexConsumer, i, o, q, r, t, v);
            EntityRenderDispatcher.shadowVertex(pose, vertexConsumer, i, o, q, s, t, w);
            EntityRenderDispatcher.shadowVertex(pose, vertexConsumer, i, p, q, s, u, w);
            EntityRenderDispatcher.shadowVertex(pose, vertexConsumer, i, p, q, r, u, v);
        }
    }

    private static void shadowVertex(PoseStack.Pose pose, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k) {
        vertexConsumer.vertex(pose.pose(), g, h, i).color(1.0f, 1.0f, 1.0f, f).uv(j, k).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(pose.normal(), 0.0f, 1.0f, 0.0f).endVertex();
    }

    public void setLevel(@Nullable Level level) {
        this.level = level;
        if (level == null) {
            this.camera = null;
        }
    }

    public double distanceToSqr(Entity entity) {
        return this.camera.getPosition().distanceToSqr(entity.position());
    }

    public double distanceToSqr(double d, double e, double f) {
        return this.camera.getPosition().distanceToSqr(d, e, f);
    }

    public Quaternion cameraOrientation() {
        return this.cameraOrientation;
    }

    public Font getFont() {
        return this.font;
    }
}
