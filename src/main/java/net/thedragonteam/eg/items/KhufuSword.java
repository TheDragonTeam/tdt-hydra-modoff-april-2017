package net.thedragonteam.eg.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.thedragonteam.eg.base.BaseSword;

import java.util.List;
import java.util.Random;

/**
 * Created by sokratis12GR on 4/20/2017.
 */
public class KhufuSword extends BaseSword {

    public static final Item.ToolMaterial KHUFU = EnumHelper.addToolMaterial("khufu", 1, 1120, 1.0F, 2.5F, 40);
    private Random random = new Random();

    public KhufuSword() {
        super(KHUFU, "khufu_sword");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        if (!worldIn.isRemote) {
            ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
            if (!player.getCooldownTracker().hasCooldown(stack.getItem())) {
                if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() == stack.getItem()) {
                    EntityLivingBase lastAttacker = player.getLastAttacker();
                    BlockPos teleportPos = new BlockPos(lastAttacker.posX + (random.nextInt(18 - 10 + 1) + 10), lastAttacker.posY, lastAttacker.posZ + (random.nextInt(18 - 10 + 1) + 10));
                    int cooldownTime = 20 * 15;
                    lastAttacker.setPositionAndUpdate(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());
                    lastAttacker.setGlowing(true);
                    stack.damageItem(50, player);
                    player.getCooldownTracker().setCooldown(stack.getItem(), cooldownTime);
                }
            }
        }
        return super.onItemRightClick(worldIn, player, handIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(1, "Right-Click to teleport attacked entities away");
        tooltip.add(2, "Cost: 50 durability");
        super.addInformation(stack, playerIn, tooltip, advanced);
    }
}