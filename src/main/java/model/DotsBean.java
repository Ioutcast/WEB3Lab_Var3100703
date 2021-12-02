package model;

import util.DBManager;
import lombok.*;
import view.AreaResult;



import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;

import javax.faces.event.ActionEvent;
import javax.inject.Named;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Named("dotsBean")
@ApplicationScoped
public class DotsBean{
   Dot dotForExecution = new Dot(); //точка для отправки
   Dot dotFromSVG = new Dot(); //точка из графика

    DBManager dbManager = new DBManager();

    //Загрузка коллекции из БД
    private List<Dot> dotsList = dbManager.getPoints();

    //Добавляет точку в коллекцию из формы.
    public void addPoint() throws IOException {
        Dot currentDot = new Dot();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        currentDot.setCurrentTime(dateFormat.format(new Date(System.currentTimeMillis())));
        //Проверка R
        try {
            if(dotForExecution.getR() >= 0 && dotForExecution.getR() != null){
                currentDot.setR(dotForExecution.getR());
            }else {
                view.ErrorPage.goToErrorPage("Wrong R");
                return;
            }
        }catch (Exception e){
            view.ErrorPage.goToErrorPage("Wrong R");
            return;
        }
        //Проверка X
        try {
            if (dotForExecution.getX() != null){
                currentDot.setX(dotForExecution.getX());
            } else{
                view.ErrorPage.goToErrorPage("Wrong X");
                return;
            }
        }catch (Exception e){
            view.ErrorPage.goToErrorPage("Wrong X");
            return;
        }
        //Проверка Y
        try {
            if (dotForExecution.getY() != null && dotForExecution.getY() >= -3 && dotForExecution.getY() <= 3){
                currentDot.setY(dotForExecution.getY());
            } else{
                view.ErrorPage.goToErrorPage("Wrong Y");
                return;
            }
        }catch (Exception e){
            view.ErrorPage.goToErrorPage("Wrong Y");
            return;
        }

        currentDot.setResult(AreaResult.isItInArea(currentDot));
        if(dbManager.addPoint(currentDot)) {
            dotsList.add(currentDot);
        }
    }

    //Добавляет точку в коллекцию и бд из графика.
    public void addDotFromSvg() throws IOException {
        Dot currentDot = new Dot();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        currentDot.setCurrentTime(dateFormat.format(new Date(System.currentTimeMillis())));
        currentDot.setR(dotFromSVG.getR());
        currentDot.setX(dotFromSVG.getX());
        currentDot.setY(dotFromSVG.getY());
        currentDot.setResult(AreaResult.isItInArea(dotFromSVG));
        if(dbManager.addPoint(currentDot)) {
            dotsList.add(currentDot);
        }
    }

}