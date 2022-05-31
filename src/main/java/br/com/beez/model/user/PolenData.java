package br.com.beez.model.user;

import br.com.beez.dto.impl.EconomyRepository;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
@Data
@Accessors(fluent = true)
public class PolenData {

    private static final Random RANDOM = new Random();
    @Getter private static final int MIN_COINS = 0;
    @Getter private static final int MAX_COINS = 25;

    private long _id;
    private double polens;
    private long dailyMillis;

    public PolenData(long discordId) {
        this._id = discordId;
        this.polens = 30;
    }

    public double checkDaily() {
        if (dailyMillis > System.currentTimeMillis()) return -1;

        val dailyReward = RANDOM.nextInt(MAX_COINS - MIN_COINS) + MIN_COINS;
        addCoins(dailyReward);

        dailyMillis = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);
        return dailyReward;
    }

    public void addCoins(double polens) {
        this.polens += polens;
    }

    public void queue() {
        EconomyRepository.instance().replace(this);
    }

}