package ua.com.smiddle.task.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.smiddle.task.core.dao.Table1DAO;
import ua.com.smiddle.task.core.dao.Table2DAO;
import ua.com.smiddle.task.core.models.Table1;
import ua.com.smiddle.task.core.models.Table2;

import javax.persistence.NoResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Added by A.Osadchuk on 04.08.2016 at 14:09.
 * Project: Task
 */
@Controller
public class Controller1 {
    @Autowired
    private Table1DAO table1DAO;
    @Autowired
    private Table2DAO table2DAO;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");


    //FilterChain
    @RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        List<Table1> table1 = table1DAO.getTable1();
        if (!table1.isEmpty()) model.addAttribute("table1", table1.get(0));
        return "index";
    }

    @RequestMapping(value = "/submitXY", method = RequestMethod.POST)
    public String submitXY(@RequestParam Long id, @RequestParam int x, @RequestParam int y, @RequestParam int z) {
        Table1 t1 = new Table1(x, y, z);
        t1.setId(id);
        table1DAO.mergeTable1(t1);
        return "redirect:/";
    }

    @RequestMapping(value = "/submitPhone", method = RequestMethod.POST)
    public String submitPhone(@RequestParam String phonenumber, @RequestParam String ban_Start_Date, @RequestParam String ban_Stop_Date, @RequestParam boolean banActive) throws ParseException {
        System.out.println("Got phonenumber=" + phonenumber + ", ban_Start_Date=" + ban_Start_Date + ", ban_Stop_Date=" + ban_Stop_Date + ", banActive=" + banActive);
        try {
            Table2 t2 = table2DAO.getTable2ByPhone(phonenumber);
            if (!banActive) table2DAO.removeTable2(t2.getId());
            else {
                t2.setBan_Start_Date(format.parse(ban_Start_Date));
                t2.setBan_Stop_Date(format.parse(ban_Stop_Date));
                table2DAO.mergeTable2(t2);
            }
        } catch (NoResultException e) {
            if (banActive) {
                Table2 t2 = new Table2(phonenumber, format.parse(ban_Start_Date), format.parse(ban_Stop_Date), banActive);
                table2DAO.mergeTable2(t2);
            }
        }
        return "redirect:/";
    }
}
