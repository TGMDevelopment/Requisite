/*
 * Requisite - Minecraft library mod
 * Copyright (C) 2021 MatthewTGM
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

package xyz.matthewtgm.requisite.core.files;

import xyz.matthewtgm.tgmconfig.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager extends Thread {

    private final Configuration configuration;
    private final List<IConfigurable> configurables;

    public ConfigurationManager(Configuration configuration) {
        this.configuration = configuration;
        this.configurables = new ArrayList<>();
    }

    public void update() {
        for (IConfigurable configurable : configurables) {
            configurable.load(this);
            configurable.save(this);
        }
    }

    public ConfigurationManager addConfigurable(IConfigurable configurable) {
        this.configurables.add(configurable);
        configurable.load(this);
        configurable.save(this);

        return this;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public List<IConfigurable> getConfigurables() {
        return configurables;
    }

}