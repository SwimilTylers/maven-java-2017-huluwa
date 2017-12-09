package platform.plate;

import utils.coordinate._2Coordinate;

public interface PlateSettings{
    _2Coordinate granularity();
    _2Coordinate start();
    int XNum();
    int YNum();

    PlateSettings Regularized = new PlateSettings() {

        public _2Coordinate granularity() {
            return _2Coordinate.Regularized_Scale;
        }


        public _2Coordinate start() {
            return _2Coordinate.Origin;
        }


        public int XNum() {
            return 15;
        }


        public int YNum() {
            return 15;
        }
    };
}
