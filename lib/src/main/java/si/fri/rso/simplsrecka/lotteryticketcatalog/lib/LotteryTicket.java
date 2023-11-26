package si.fri.rso.simplsrecka.lotteryticketcatalog.lib;

import java.time.Instant;

public class LotteryTicket {

    private Integer ticketId;
    private String name;
    private String description;
    private Double price;
    private Instant created;
    private Instant drawDate;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(Instant drawDate) {
        this.drawDate = drawDate;
    }
}
