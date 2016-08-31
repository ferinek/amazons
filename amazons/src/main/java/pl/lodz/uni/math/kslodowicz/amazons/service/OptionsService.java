package pl.lodz.uni.math.kslodowicz.amazons.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafx.scene.control.ChoiceDialog;
import pl.lodz.uni.math.kslodowicz.amazons.dao.OptionsDao;

@Service
public class OptionsService {

    @Autowired
    private OptionsDao optionDao;

    public long getMonteCarloTime() {
        return optionDao.getMonteCarloTimeValue() * 1000L;
    }

    public void ChangeMonteCarloTime() {
        String actual = (int) getMonteCarloTime() / 1000 + " sec";
        
        List<String> choices = new ArrayList<>();
        choices.add("1 sec");
        choices.add("2 sec");
        choices.add("3 sec");
        choices.add("4 sec");
        choices.add("5 sec");
        choices.add("10 sec");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(actual, choices);
        dialog.setTitle("Options");
        dialog.setHeaderText("Set calculation time for Hard AI");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            Integer value=Integer.parseInt(result.get().split(" ")[0]);
            optionDao.setMonteCarloTimeValue(value);

        }

    }

}
