/*
 * Requisite - Minecraft library mod
 *  Copyright (C) 2021 Qalcyo
 *
 * Requisite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Requisite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Requisite. If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.qalcyo.requisite.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import xyz.qalcyo.requisite.Requisite;

import java.util.List;

public class CosmeticRenderer implements LayerRenderer<AbstractClientPlayer> {

    private final CosmeticManager cosmeticManager;

    public CosmeticRenderer(CosmeticManager cosmeticManager) {
        this.cosmeticManager = cosmeticManager;
    }

    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float tickAge, float netHeadYaw, float netHeadPitch, float scale) {
        if (Requisite.getInstance().getCosmeticConfigurations().areCosmeticsEnabled()) {
            String uuid = player.getUniqueID().toString();
            if (cosmeticManager.getPlayerData().containsKey(uuid)) {
                PlayerCosmeticHolder cosmeticHolder = cosmeticManager.getPlayerData().get(uuid);
                if (cosmeticHolder != null && cosmeticHolder.getEnabled() != null && !cosmeticHolder.getEnabled().isEmpty()) {
                    List<BaseCosmetic> enabled = cosmeticHolder.getEnabled();
                    for (BaseCosmetic cosmetic : enabled) {
                        cosmetic.render(player, limbSwing, limbSwingAmount, partialTicks, tickAge, netHeadYaw, netHeadPitch, scale);
                    }
                }
            }
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }

}