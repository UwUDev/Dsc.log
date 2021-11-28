package me.uwu.dsc.log.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum EventType {
    ACK("ACK", "Ack"),
    CHANNEL_CREATE("CHANNEL_CREATE", "ChannelCreated"),
    CHANNEL_DELETE("CHANNEL_DELETE", "ChannelDeleted"),
    CHANNEL_UNREAD_UPDATE("CHANNEL_UNREAD_UPDATE", "ChannelUnreadMessage"),
    CHANNEL_UPDATE("CHANNEL_UPDATE", "ChannelUpdated"),
    GUILD_BAN_ADD("GUILD_BAN_ADD", "AddedGuildBan"),
    GUILD_EMOJIS_UPDATE("GUILD_EMOJIS_UPDATE", "GuildEmojiUpdated"),
    GUILD_INTEGRATIONS_UPDATE("GUILD_INTEGRATIONS_UPDATE", "GuildIntegrationsUpdated"),
    GUILD_MEMBER_UPDATE("GUILD_MEMBER_UPDATE", "GuildMemberUpdated"),
    HEARTBEAT("HEARTBEAT", "Heartbeat"),
    INTEGRATION_UPDATE("INTEGRATION_UPDATE", "IntegrationUpdated"),
    MESSAGE_ACK("MESSAGE_ACK", "MessageACK"),
    MESSAGE_RECEIVED("MESSAGE_CREATE", "MessageReceived"),
    MESSAGE_DELETE("MESSAGE_DELETE", "MessageDeleted"),
    MESSAGE_REACTION_ADD("MESSAGE_REACTION_ADD", "ReactionAdded"),
    MESSAGE_REACTION_REMOVE("MESSAGE_REACTION_REMOVE", "ReactionRemoved"),
    MESSAGE_UPDATE("MESSAGE_UPDATE", "MessageUpdated"),
    PRESENCE_UPDATE("PRESENCE_UPDATE", "PresenceUpdated"),
    READY("READY", "ConnectionSucceeded"),
    RELATIONSHIP_ADD("RELATIONSHIP_ADD", "FriendRequestIncoming"),
    RELATIONSHIP_REMOVE("RELATIONSHIP_REMOVE", "FriendRemoved"),
    SESSIONS_REPLACE("SESSIONS_REPLACE", ""),
    UNKNOWN("UNKNOWN", "UnknownEvent"),
    VOICE_STATE_UPDATE("VOICE_STATE_UPDATE", "VoiceStateUpdated");

    private final String identifier;
    private final String name;
}
