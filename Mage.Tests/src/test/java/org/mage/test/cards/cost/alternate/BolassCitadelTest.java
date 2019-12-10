package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BolassCitadelTest extends CardTestPlayerBase {
    @Test
    public void testCastEagerCadet() {
        /*
         * Eager Cadet
         * Creature — Human Soldier
         * 1/1
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Eager Cadet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eager Cadet");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Eager Cadet", 1);
        assertGraveyardCount(playerA,0);
        assertLife(playerA,  19);
    }

    @Test
    public void testCastTreatsToShare() {
        /*
         * Curious Pair {1}{G}
         * Creature — Human Peasant
         * 1/3
         * ----
         * Treats to Share {G}
         * Sorcery — Adventure
         * Create a Food token.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        assertTapped("Forest", false);
        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food", 1);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA,0);
        assertLife(playerA,  19);
    }
}
