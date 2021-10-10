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

package xyz.qalcyo.requisite;

import net.minecraft.client.gui.screen.Screen;
import xyz.qalcyo.mango.Multithreading;
import xyz.qalcyo.requisite.commands.CommandHelper;
import xyz.qalcyo.requisite.core.IEventListener;
import xyz.qalcyo.requisite.core.RequisiteAPI;
import xyz.qalcyo.requisite.core.RequisiteEventManager;
import xyz.qalcyo.requisite.core.commands.CommandRegistry;
import xyz.qalcyo.requisite.core.files.ConfigurationManager;
import xyz.qalcyo.requisite.core.files.FileManager;
import xyz.qalcyo.requisite.core.integration.mods.IModConfigurationMenu;
import xyz.qalcyo.requisite.core.keybinds.KeyBindRegistry;
import xyz.qalcyo.requisite.core.networking.RequisiteClientSocket;
import xyz.qalcyo.requisite.core.rendering.IEnhancedFontRenderer;
import xyz.qalcyo.requisite.core.util.*;
import xyz.qalcyo.requisite.core.util.messages.IMessageQueue;
import xyz.qalcyo.requisite.core.gui.factory.IComponentFactory;
import xyz.qalcyo.requisite.gui.factory.ComponentFactory;
import xyz.qalcyo.requisite.integration.mods.ModIntegration;
import xyz.qalcyo.requisite.networking.SocketHelper;
import xyz.qalcyo.requisite.notifications.Notifications;
import xyz.qalcyo.requisite.rendering.EnhancedFontRenderer;
import xyz.qalcyo.requisite.util.*;

import java.io.File;

public class Requisite implements RequisiteAPI {

    private static final Requisite INSTANCE = new Requisite();
    private static boolean initialized = false;

    private FileManager fileManager;
    private ConfigurationManager configurationManager;
    private Notifications notifications;
    private RequisiteClientSocket requisiteSocket;
    private ModIntegration modIntegration;
    private CommandRegistry commandRegistry;
    private KeyBindRegistry keyBindRegistry;
    private ComponentFactory componentFactory;
    private RequisiteEventManager internalEventManager;

    private EnhancedFontRenderer enhancedFontRenderer;
    private GuiHelper guiHelper;
    private PlayerHelper playerHelper;
    private ChatHelper chatHelper;
    private MouseHelper mouseHelper;
    private RenderHelper renderHelper;
    private MessageQueue messageQueue;
    private ServerHelper serverHelper;
    private GlHelper glHelper;

    public boolean initialize(File gameDir) {
        if (initialized)
            return false;

        /* Initialize services. */
        fileManager = new FileManager(this);
        configurationManager = new ConfigurationManager("config", fileManager.getRequisiteDirectory(fileManager.getQalcyoDirectory(fileManager.getConfigDirectory(gameDir))));
        notifications = new Notifications(this);
        (requisiteSocket = new RequisiteClientSocket(this, new SocketHelper())).awaitConnect();
        modIntegration = new ModIntegration();
        commandRegistry = new CommandRegistry(new CommandHelper());
        keyBindRegistry = new KeyBindRegistry(this);
        componentFactory = new ComponentFactory();
        internalEventManager = new RequisiteEventManager();

        /* Initialize utilities. */
        Multithreading.runAsync(() -> {
            enhancedFontRenderer = new EnhancedFontRenderer();
            guiHelper = new GuiHelper();
            playerHelper = new PlayerHelper();
            chatHelper = new ChatHelper();
            mouseHelper = new MouseHelper();
            //positionHelper = new PositionHelper();
            renderHelper = new RenderHelper();
            messageQueue = new MessageQueue(this);
            serverHelper = new ServerHelper();
            glHelper = new GlHelper();
        });

        return initialized = true;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public Notifications getNotifications() {
        return notifications;
    }

    public RequisiteClientSocket getRequisiteSocket() {
        return requisiteSocket;
    }

    public ModIntegration getModIntegration() {
        return modIntegration;
    }

    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    public KeyBindRegistry getKeyBindRegistry() {
        return keyBindRegistry;
    }

    public ComponentFactory getComponentFactory() {
        return componentFactory;
    }

    public RequisiteEventManager getInternalEventManager() {
        return internalEventManager;
    }

    public IEventListener getInternalEventListener() {
        throw new UnsupportedOperationException("Requisite 1.17.1 does not support the internal event listener.");
    }

    public void openMenu() {

    }

    public IModConfigurationMenu createMainMenu() {
        return null;
    }

    public EnhancedFontRenderer getEnhancedFontRenderer() {
        return enhancedFontRenderer;
    }

    public GuiHelper getGuiHelper() {
        return guiHelper;
    }

    public PlayerHelper getPlayerHelper() {
        return playerHelper;
    }

    public ChatHelper getChatHelper() {
        return chatHelper;
    }

    public MouseHelper getMouseHelper() {
        return mouseHelper;
    }

    public IPositionHelper getPositionHelper() {
        return null;
    }

    public RenderHelper getRenderHelper() {
        return renderHelper;
    }

    public MessageQueue getMessageQueue() {
        return messageQueue;
    }

    public ServerHelper getServerHelper() {
        return serverHelper;
    }

    public GlHelper getGlHelper() {
        return glHelper;
    }

    public static Requisite getInstance() {
        return INSTANCE;
    }

    public static boolean isInitialized() {
        return initialized;
    }

}