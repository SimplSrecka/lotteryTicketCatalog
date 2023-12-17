package si.fri.rso.simplsrecka.lotteryticketcatalog.models.converters;

import si.fri.rso.simplsrecka.lotteryticketcatalog.models.entities.LotteryTicketEntity;
import si.fri.rso.simplsrecka.lotteryticketcatalog.lib.LotteryTicket;

public class LotteryTicketConverter {

    public static LotteryTicket toDto(LotteryTicketEntity entity) {
        LotteryTicket dto = new LotteryTicket();
        dto.setTicketId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    public static LotteryTicketEntity toEntity(LotteryTicket dto) {
        LotteryTicketEntity entity = new LotteryTicketEntity();
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());

        return entity;
    }

}