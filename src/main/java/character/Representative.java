package character;

import utils.layout.LayoutBrief;
import utils.sorter.ComparingInterface;
import utils.sorter.Sorter;

public interface Representative {
    void DefaultConstituents(LayoutBrief init);
//    void AddRepresent(Beings... obj);
    void RangeConstituents(LayoutBrief layout);
    void SortConstituents(Sorter sorter, ComparingInterface cmpInterface);
    Beings Hail(String name);
}
