package si.fri.rso.simplsrecka.lotteryticketcatalog.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import org.eclipse.microprofile.metrics.annotation.Timed;

import si.fri.rso.simplsrecka.lotteryticketcatalog.lib.LotteryTicket;
import si.fri.rso.simplsrecka.lotteryticketcatalog.models.converters.LotteryTicketConverter;
import si.fri.rso.simplsrecka.lotteryticketcatalog.models.entities.LotteryTicketEntity;


@RequestScoped
public class LotteryCatalogBean {

    private Logger log = Logger.getLogger(LotteryCatalogBean.class.getName());

    @Inject
    private EntityManager em;

    public List<LotteryTicket> getLotteryTickets() {
        TypedQuery<LotteryTicketEntity> query = em.createNamedQuery("LotteryTicketEntity.getAll", LotteryTicketEntity.class);
        List<LotteryTicketEntity> resultList = query.getResultList();
        return resultList.stream().map(LotteryTicketConverter::toDto).collect(Collectors.toList());
    }

    @Timed
    public List<LotteryTicket> getLotteryTicketsFilter(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();
        return JPAUtils.queryEntities(em, LotteryTicketEntity.class, queryParameters).stream()
                .map(LotteryTicketConverter::toDto).collect(Collectors.toList());
    }

    public LotteryTicket getLotteryTicket(Integer id) {
        LotteryTicketEntity lotteryTicketEntity = em.find(LotteryTicketEntity.class, id);
        if (lotteryTicketEntity == null) {
            throw new NotFoundException();
        }
        return LotteryTicketConverter.toDto(lotteryTicketEntity);
    }

    public LotteryTicket createLotteryTicket(LotteryTicket lotteryTicket) {
        LotteryTicketEntity lotteryTicketEntity = LotteryTicketConverter.toEntity(lotteryTicket);
        try {
            beginTx();
            em.persist(lotteryTicketEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }
        if (lotteryTicketEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }
        return LotteryTicketConverter.toDto(lotteryTicketEntity);
    }

    public LotteryTicket updateLotteryTicket(Integer id, LotteryTicket lotteryTicket) {
        LotteryTicketEntity entity = em.find(LotteryTicketEntity.class, id);
        if (entity == null) {
            return null;
        }
        LotteryTicketEntity updatedEntity = LotteryTicketConverter.toEntity(lotteryTicket);
        try {
            beginTx();
            updatedEntity.setId(entity.getId());
            updatedEntity = em.merge(updatedEntity);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }
        return LotteryTicketConverter.toDto(updatedEntity);
    }

    public boolean deleteLotteryTicket(Integer id) {
        LotteryTicketEntity lotteryTicket = em.find(LotteryTicketEntity.class, id);
        if (lotteryTicket != null) {
            try {
                beginTx();
                em.remove(lotteryTicket);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else {
            return false;
        }
        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
