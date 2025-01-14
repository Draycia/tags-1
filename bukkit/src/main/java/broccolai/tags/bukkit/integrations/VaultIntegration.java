package broccolai.tags.bukkit.integrations;

import broccolai.tags.api.events.EventListener;
import broccolai.tags.api.events.event.TagChangeEvent;
import broccolai.tags.api.events.event.UserLoginEvent;
import broccolai.tags.api.model.tag.Tag;
import broccolai.tags.api.model.user.TagsUser;
import broccolai.tags.api.service.TagsService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.event.PostOrders;
import net.kyori.event.method.annotation.PostOrder;
import net.kyori.event.method.annotation.Subscribe;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

@Singleton
public final class VaultIntegration implements EventListener {

    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.builder()
            .hexColors()
            .character(LegacyComponentSerializer.SECTION_CHAR)
            .build();

    private final Chat chat;
    private final TagsService tagsService;

    @Inject
    public VaultIntegration(
            final @NonNull Chat chat,
            final @NonNull TagsService tagsService
    ) {
        this.chat = chat;
        this.tagsService = tagsService;
    }

    @Subscribe
    public void onUserLogin(final @NonNull UserLoginEvent event) {
        TagsUser user = event.user();
        Tag tag = this.tagsService.load(user);
        String tagPrefix = FontImageWrapper.replaceFontImages(LEGACY.serialize(tag.component()));
        this.chat.setPlayerPrefix(null, Bukkit.getPlayer(user.uuid()), tagPrefix);
    }

    @Subscribe
    @PostOrder(PostOrders.LATE)
    public void onTagChange(final @NonNull TagChangeEvent event) {
        Player player = Bukkit.getPlayer(event.user().uuid());
        String tagPrefix = FontImageWrapper.replaceFontImages(LEGACY.serialize(event.tag().component()));
        this.chat.setPlayerPrefix(null, Bukkit.getPlayer(player.getUniqueId()), tagPrefix);
    }

}
