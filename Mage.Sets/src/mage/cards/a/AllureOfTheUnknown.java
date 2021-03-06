package mage.cards.a;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AllureOfTheUnknown extends CardImpl {

    public AllureOfTheUnknown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");

        // Reveal the top six cards of your library. An opponent exiles a nonland card from among them, then you put the rest into your hand. That opponent may cast the exiled card without paying its mana cost.
        this.getSpellAbility().addEffect(new AllureOfTheUnknownEffect());
    }

    private AllureOfTheUnknown(final AllureOfTheUnknown card) {
        super(card);
    }

    @Override
    public AllureOfTheUnknown copy() {
        return new AllureOfTheUnknown(this);
    }
}

class AllureOfTheUnknownEffect extends OneShotEffect {

    AllureOfTheUnknownEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top six cards of your library. " +
                "An opponent exiles a nonland card from among them, " +
                "then you put the rest into your hand. " +
                "That opponent may cast the exiled card without paying its mana cost.";
    }

    private AllureOfTheUnknownEffect(final AllureOfTheUnknownEffect effect) {
        super(effect);
    }

    @Override
    public AllureOfTheUnknownEffect copy() {
        return new AllureOfTheUnknownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        player.revealCards(source, cards, game);
        if (cards.count(StaticFilters.FILTER_CARD_NON_LAND, game) == 0) {
            return player.moveCards(cards, Zone.HAND, source, game);
        }
        TargetOpponent targetOpponent = new TargetOpponent(true);
        if (!player.choose(outcome, targetOpponent, source.getSourceId(), game)) {
            return false;
        }
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        TargetCard targetCard = new TargetCardInLibrary(StaticFilters.FILTER_CARD_A_NON_LAND);
        opponent.choose(Outcome.Exile, cards, targetCard, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (player.moveCards(card, Zone.EXILED, source, game)
                && card != null
                && game.getState().getZone(card.getId()) == Zone.EXILED) {
            cards.remove(card);
        }
        player.moveCards(cards, Zone.HAND, source, game);
        if (opponent.chooseUse(outcome, "Cast the exiled card without paying its mana cost?", source, game)) {
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            opponent.cast(opponent.chooseAbilityForCast(card, game, true),
                    game, true, new MageObjectReference(source.getSourceObject(game), game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        }
        return true;
    }
}