package monster;

import item.repository.ConsumableRepository;
import item.repository.EquipmentRepository;
import player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created on 2017/8/8.
 * Description:
 * @author Liao
 */
public class Frog extends Monster{

    public Frog() {
        super("Frog", "I have lots of life experience", 150, 20, 15);
    }

    @Override
    public void attack(Player player) {

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
