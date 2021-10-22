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

package xyz.qalcyo.requisite.core.localization;

import xyz.qalcyo.json.entities.JsonObject;

public class DefaultModLocale {

    private final JsonObject content = new JsonObject();

    public DefaultModLocale add(String category, String key, String translation) {
        content.addIfAbsent(category, new JsonObject());

        JsonObject cata = content.getAsObject(category);
        cata.add(key, translation);
        content.add(category, cata);

        return this;
    }

    public DefaultModLocale add(String key, String translation) {
        content.add(key, translation);
        return this;
    }

    public ModLocale build() {
        return new ModLocale(content);
    }

    public JsonObject getContent() {
        return content.copy();
    }

}