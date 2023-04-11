package net.xcu.autocoord.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.xcu.autocoord.AutoCoord;
import java.util.ArrayList;
import java.util.List;

public class MacroHandler {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static final double[] coords = {0.0, 0.0, 0.0};
    private static final List<Entity> blockers = new ArrayList<Entity>();
    private static final Direction[] direction = new Direction[2];
    private static double playerPosY = 0.0;
    private static final int CD = 4;
    private static int cd = 0;
    private static boolean enableAutoYaw = false;

    public static boolean toggleAutoYaw() {
        enableAutoYaw = !enableAutoYaw;
        return enableAutoYaw;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END || mc.thePlayer == null)
            return;
        if (mc.thePlayer.posY != playerPosY)
            clearBlocker(mc.thePlayer.worldObj);
        if (cd > 0)
            cd--;
        if (AutoCoord.MACRO_AC.isKeyDown() && cd == 0) {
            cd = CD;
            if (blockers.isEmpty()){
                onStartAC();
            }else {
                clearBlocker(mc.thePlayer.worldObj);
            }
        }
    }
    public static void onStartAC() {
        if (mc.thePlayer == null) return;
        World world = mc.thePlayer.worldObj;
        if (mc.thePlayer.posX > coords[0]) {
            direction[0] = Direction.W;
        }else direction[0] = Direction.E;
        if (mc.thePlayer.posZ > coords[1]) {
            direction[1] = Direction.N;
        }else direction[1] = Direction.S;
        clearBlocker(world);
        playerPosY = mc.thePlayer.posY;
        spawnBlocker(world, coords[0], coords[1], direction[0], mc.thePlayer);
        spawnBlocker(world, coords[0], coords[1], direction[1], mc.thePlayer);
        if (enableAutoYaw) {
            if (coords[2] < 0.0) coords[2] += 360.0;
            mc.thePlayer.rotationYaw = (float) coords[2];
            mc.thePlayer.prevRotationYaw = (float) coords[2];
            mc.thePlayer.setRotationYawHead((float) coords[2]);
            mc.thePlayer.rotationYawHead = (float) coords[2];
            mc.thePlayer.prevRotationYawHead = (float) coords[2];
        }
    }
    private static void spawnBlocker(World world, double x, double z, Direction direction, EntityPlayer player) {
        EntityBoat barrier = new EntityBoat(world);
        switch (direction) {
            case S:
                barrier.setPosition(x, player.posY + 1.5, z + 1.05);
                break;
            case N:
                barrier.setPosition(x, player.posY + 1.5, z - 1.05);
                break;
            case W:
                barrier.setPosition(x - 1.05, player.posY + 1.5, z);
                break;
            case E:
                barrier.setPosition(x + 1.05, player.posY + 1.5, z);
                break;
        }
        world.spawnEntityInWorld(barrier);
        blockers.add(barrier);
    }
    private static void clearBlocker(World world) {
        for (Entity entity: blockers) {
            world.removeEntity(entity);
        }
        blockers.clear();
    }
    private enum Direction {
        N, S, W, E
    }
}
