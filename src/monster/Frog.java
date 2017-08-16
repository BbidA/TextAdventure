package monster;

import io.MessageHelper;
import io.MessageType;
import player.Player;
import repository.ConsumableRepository;
import repository.EquipmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created on 2017/8/8.
 * Description:
 * @author Liao
 */
public class Frog extends Monster {

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
        player.setLifeValue(player.getLifeValue() - healthReduction);
        String battleMessage = "Frog gives you " + healthReduction + " damage" + System.lineSeparator()
                + "Your current health: " + player.getLifeValue();
        MessageHelper.printMessage(battleMessage, MessageType.PROMPT);
    }

    @Override
    protected ItemBag generateItem() {

        Random random = new Random();
        int randNum = random.nextInt(10);
        if (randNum < 2) {
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
