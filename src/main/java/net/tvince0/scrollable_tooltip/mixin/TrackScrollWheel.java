package net.tvince0.scrollable_tooltip.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.InputUtil;

import net.tvince0.scrollable_tooltip.ScrollableTooltip;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class TrackScrollWheel {
    @Inject(method = "onMouseScroll(JDD)V", at = @At("HEAD"))
    private void trackWheel (long window, double horizontal, double vertical, CallbackInfo info) {
        long mcHandle = MinecraftClient.getInstance().getWindow().getHandle();
        if (vertical > 0) {
            if (InputUtil.isKeyPressed(mcHandle, GLFW.GLFW_KEY_LEFT_SHIFT)) {
                ScrollableTooltip.scrollRight();
            }
            else {
                ScrollableTooltip.scrollDown();
            }
        }
        else if (vertical < 0) {
            if (InputUtil.isKeyPressed(mcHandle, GLFW.GLFW_KEY_LEFT_SHIFT)) {
                ScrollableTooltip.scrollLeft();
            }
            else {
                ScrollableTooltip.scrollUp();
            }
        }
    }
}
