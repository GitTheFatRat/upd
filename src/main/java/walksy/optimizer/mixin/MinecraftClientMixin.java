package walksy.optimizer.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.optimizer.Optimizer;
import walksy.optimizer.WalksyCrystalOptimizer;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Inject(method = "doItemUse", at = @At("HEAD"), cancellable = true)
	private void onDoItemUse(CallbackInfo ci) {
		MinecraftClient client = (MinecraftClient) (Object) this;
		if (client.player == null) {
			return;
		}
		Optimizer optimizer = WalksyCrystalOptimizer.getOptimizer();
		if (optimizer == null) {
			return;
		}
		ItemStack stack = client.player.getMainHandStack();
		if (stack.isOf(Items.END_CRYSTAL) && optimizer.stopItemUse(stack)) {
			ci.cancel();
		}
	}
}
