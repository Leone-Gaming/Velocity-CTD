/*
 * Copyright (C) 2024 Velocity Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.velocitypowered.proxy.config.migration;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import org.apache.logging.log4j.Logger;

/**
 * Creation of the configuration option "allow-illegal-characters-in-chat".
 * Creation of the configuration option "disable-forge".
 * Creation of the configuration option "enable-dynamic-fallbacks".
 * Creation of the configuration option "enforce-chat-signing".
 * Creation of the configuration option "fallback-version-ping".
 * Creation of the configuration option "log-offline-connections".
 * Creation of the configuration option "log-player-connections".
 * Creation of the configuration option "log-player-disconnections".
 * Creation of the configuration option "minimum-version".
 * Creation of the configuration option "outdated-version-ping".
 * Creation of the configuration option "server-brand".
 * Creation of the configuration option "translate-header-footer".
 */
public final class VelocityCustomFeatureMigration implements ConfigurationMigration {
  @Override
    public boolean shouldMigrate(final CommentedFileConfig config) {
    return configVersion(config) < 2.7;
  }

  @Override
    public void migrate(final CommentedFileConfig config, final Logger logger) {
    // Illegal chat characters in chat
    config.set("advanced.allow-illegal-characters-in-chat", false);
    config.setComment("advanced.allow-illegal-characters-in-chat", """
            Enables the execution of illegal characters in chat and only allows
            or denies illegal characters that are executed through the proxy.""");
    // Forge inbound handshakes handled as vanilla
    config.set("disable-forge", false);
    config.setComment("disable-forge", """
            If true, disables handling of inbound Forge handshakes.""");
    // Cycles users through least populated server instead of first fallback
    config.set("enable-dynamic-fallbacks", false);
    config.setComment("enable-dynamic-fallbacks", """
            Sends you to the least populated fallback server, instead of the first available fallback server.
            Keep this false if you only have one fallback server.""");
    // Disables signature kicking when in invalid chat state
    config.set("enforce-chat-signing", false);
    config.setComment("enforce-chat-signing", """
            Whether chat signing should be enforced. If disabled, backend servers MUST disable chat signing.""");
    // Disables the logging of users not on an official Minecraft account
    config.set("log-offline-connections", true);
    config.setComment("log-offline-connections", """
            If false, disables logging for offline player connections.""");
    // Disables the logging of user connections
    config.set("log-player-connections", true);
    config.setComment("log-player-connections", """
            Enables logging of player connections and by default, still displays
            player disconnections and initial connections.""");
    // Disables the logging of user disconnections
    config.set("log-player-disconnections", true);
    config.setComment("log-player-disconnections", """
            Enables logging of player disconnection and by default, still displays
            player connections and initial connections.""");
    // A performance-based feature that doesn't process header and footer components for better performance
    config.set("translate-header-footer", true);
    config.setComment("translate-header-footer", """
            If false, disables processing of header and footer translations for better performance.""");
    // Defines minimum connectable version through proxy under modern forwarding determinator
    config.set("minimum-version", "1.7.2");
    config.setComment("minimum-version", """
            Modify the minimum version, so the proxy blocks out users on the wrong version, rather than the backend server.
            Modern forwarding supports 1.13, at minimum. Set this to 1.13 or above if you are using modern forwarding.""");
    // Determines whether console should log users that are joining on an unsupported version
    config.set("log-minimum-version", false);
    config.setComment("log-minimum-version", """
            If true, a message is pasted in console displaying whether a user joined on an unsupported version.
            This corresponds with the "minimum-version" and "modern-forwarding-needs-new-client" values.""");
    // Customizes debug screen server brand
    config.set("advanced.server-brand", "{0} ({1})");
    config.setComment("advanced.server-brand", """
            Modifies the server brand that displays in your debug menu.""");
    // Customizes outdated server ping display
    config.set("advanced.outdated-version-ping", "{protocol-min}-{protocol-max} ({proxy-brand})");
    config.setComment("advanced.outdated-version-ping", """
            Modifies the server version that displays in some server status pinging sites.""");
    // Customizes server ping display
    config.set("advanced.fallback-version-ping", "{proxy-brand} {protocol-min}-{protocol-max}");
    config.setComment("advanced.fallback-version-ping", """
            Modifies the server version that displays in the multiplayer menu when no passthrough occurs.""");
    config.set("config-version", "2.7");
  }
}
