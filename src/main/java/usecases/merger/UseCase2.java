package usecases.merger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sivag on 1/2/17.
 */
public class UseCase2 {
    /**
     * Below logic written in assumption that  the use-case independent.
     * @param list
     */
    public void merge(List<Employee> list) {
        Iterator<Employee> iterator1 = list.iterator();
        List<Employee> filteredList = new ArrayList<Employee>();
        while (iterator1.hasNext()){
            Employee emp = iterator1.next();
            Iterator<Employee> iterator2 = filteredList.iterator();
            boolean emplyeeExists = false;
            /**
             * Below logic written in assumption that  the use-case independent.
             * Can be done easily by using contains() on list instead traversing through list everytime.
             */
            while (iterator2.hasNext()){
                if(emp.equals(iterator1.next())) {
                    emplyeeExists = true;
                    break;
                }
            }
            if(!emplyeeExists)
               filteredList.add(iterator1.next());
        }
    }
}

