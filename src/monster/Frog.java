package monster;

import io.MessageHelper;
import io.MessageType;
import repository.ConsumableRepository;
import repository.EquipmentRepository;
import player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created on 2017/8/8.
 * Description:
 * @author Liao
 */
public class Frog extends Monster{

    public Frog(int riskLevel) {
        super("Frog", "I have lots of life experience", 150, 20, 15);
        setExpBase(20);
        setGoldBase(50);
        setRiskLevel(riskLevel);
    }

    @Override
    public void attack(Player player) {
        int playerDefence = player.getDefence();
        int healthReduction = attack * attack / (attack + playerDefence);
        MessageHelper.printMessage("Frog gives you " + healthReduction + " damage", MessageType.PLAIN);
        player.setLifeValue(player.getLifeValue() - healthReduction);
        MessageHelper.printMessage("Your current health: " + player.getLifeValue(), MessageType.PLAIN);
    }

    @Override
    protected ItemBag generateItem() {

        Random random = new Random();
        int randNum = random.nextInt(10);
        if (randNum < 5) {
            // Generate Equipment
            List<String> equipmentRange = Arrays.asList("sword", "hammer", "armour");
            return new ItemBag(EquipmentRepository.INSTANCE.getRandomEquipment(equipmentRange));
        } else {
            // Generate Consumables
            List<String> consumableRange = Arrays.asList("health potion(small)", "bread");
            return new ItemBag(ConsumableRepository.INSTANCE.getRandomConsumable(consumableRange), random.nextInt(5));
        }
    }
}
