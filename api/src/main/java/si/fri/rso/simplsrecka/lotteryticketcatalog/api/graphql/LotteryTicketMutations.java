package si.fri.rso.simplsrecka.lotteryticketcatalog.api.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import si.fri.rso.simplsrecka.lotteryticketcatalog.lib.LotteryTicket;
import si.fri.rso.simplsrecka.lotteryticketcatalog.services.beans.LotteryCatalogBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class LotteryTicketMutations {

    @Inject
    private LotteryCatalogBean lotteryCatalogBean;

    @GraphQLMutation
    public LotteryTicket addLotteryTicket(@GraphQLArgument(name = "lotteryTicket") LotteryTicket lotteryTicket) {
        lotteryCatalogBean.createLotteryTicket(lotteryTicket);
        return lotteryTicket;
    }

    @GraphQLMutation
    public DeleteResponse deleteLotteryTicket(@GraphQLArgument(name = "id") Integer id) {
        return new DeleteResponse(lotteryCatalogBean.deleteLotteryTicket(id));
    }

}
