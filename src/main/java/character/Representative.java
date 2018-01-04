package character;

import utils.layout.LayoutBrief;
import utils.sorter.ComparingInterface;
import utils.sorter.Sorter;

public interface Representative {
    @Deprecated
    void DefaultConstituents(LayoutBrief init);
//    void AddRepresent(Beings... obj);
    @Deprecated
    void RangeConstituents(LayoutBrief layout);
    @Deprecated
    void SortConstituents(Sorter sorter, ComparingInterface cmpInterface);
    @Deprecated
    Beings Hail(String name);
}
