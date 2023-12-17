package si.fri.rso.simplsrecka.lotteryticketcatalog.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "lottery_ticket")
@NamedQueries(value =
        {
                @NamedQuery(name = "LotteryTicketEntity.getAll",
                        query = "SELECT lt FROM LotteryTicketEntity lt")
        })
public class LotteryTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}