package net.tvince0.scrollable_tooltip.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;

import net.tvince0.scrollable_tooltip.ScrollableTooltip;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class AlterPosition {
	@Inject (method = "close()V", at = @At("HEAD"))
	public void resetTrackerOnScreenClose (CallbackInfo info) {
		ScrollableTooltip.reset();
	}

	@Inject (method = "renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;II)V", at = @At("HEAD"))
	public void updateTracker (MatrixStack matrices, List<Text> lines, int x, int y, CallbackInfo info) {
		ScrollableTooltip.setItem(lines);
	}

	@Inject (method = "renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;II)V", at = @At("HEAD"))
	public void updateTracker (MatrixStack matrices, Text text, int x, int y, CallbackInfo info) {
		List<Text> asList = Arrays.asList(text);
		ScrollableTooltip.setItem(asList);
	}

	@Inject (method = "renderOrderedTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V"))
	public void applyTracker (MatrixStack matrices, List<? extends OrderedText> lines, int x, int y, CallbackInfo info) {
		long mcHandle = MinecraftClient.getInstance().getWindow().getHandle();
		if (InputUtil.isKeyPressed(mcHandle, GLFW.GLFW_KEY_PAGE_UP)) {
			if (InputUtil.isKeyPressed(mcHandle, GLFW.GLFW_KEY_LEFT_SHIFT)) {
				ScrollableTooltip.scrollLeft();
			}
			else {
				ScrollableTooltip.scrollUp();
			}
		}
		else if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_PAGE_DOWN)) {
			if (InputUtil.isKeyPressed(mcHandle, GLFW.GLFW_KEY_LEFT_SHIFT)) {
				ScrollableTooltip.scrollRight();
			}
			else {
				ScrollableTooltip.scrollDown();
			}
		}
	}

	@ModifyVariable(method = "renderOrderedTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;II)V", ordinal = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.BEFORE))
	private int modifyYAxis (int y) {
		return y + ScrollableTooltip.currentYOffset;
	}

	@ModifyVariable (method = "renderOrderedTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;II)V", ordinal = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.BEFORE))
	private int modifyXAxis (int x) {
		return x + ScrollableTooltip.currentXOffset;
	}
}
