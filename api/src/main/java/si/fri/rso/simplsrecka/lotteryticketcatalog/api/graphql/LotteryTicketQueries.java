package si.fri.rso.simplsrecka.lotteryticketcatalog.api.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.simplsrecka.lotteryticketcatalog.lib.LotteryTicket;
import si.fri.rso.simplsrecka.lotteryticketcatalog.services.beans.LotteryCatalogBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class LotteryTicketQueries {

    @Inject
    private LotteryCatalogBean lotteryCatalogBean;

    @GraphQLQuery
    public PaginationWrapper<LotteryTicket> allLotteryTickets(@GraphQLArgument(name = "pagination") Pagination pagination,
                                                              @GraphQLArgument(name = "sort") Sort sort,
                                                              @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(lotteryCatalogBean.getLotteryTickets(), pagination, sort, filter);
    }

    @GraphQLQuery
    public LotteryTicket getLotteryTciket(@GraphQLArgument(name = "id") Integer id) {
        return lotteryCatalogBean.getLotteryTicket(id);
    }

}