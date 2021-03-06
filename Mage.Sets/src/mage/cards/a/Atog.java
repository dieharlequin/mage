
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class Atog extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public Atog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ATOG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    public Atog(final Atog card) {
        super(card);
    }

    @Override
    public Atog copy() {
        return new Atog(this);
    }
}
