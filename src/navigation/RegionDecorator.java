package navigation;

/**
 * Created on 2017/8/9.
 * Description:
 * @author Liao
 */
public abstract class RegionDecorator extends Region{
    protected Region region;

    public RegionDecorator(String description, Point point, Region region) {
        super(description, point);
        this.region = region;
    }
}
