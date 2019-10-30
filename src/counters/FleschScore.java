package counters;

import java.text.DecimalFormat;

public class FleschScore {
    public double getFleschScore(int sentences, int syllables, int words) {
        double fleschScore =
                206.835 - 1.015 * ((double)words / (double)sentences) - 84.6 * ((double)syllables / (double)words);
        DecimalFormat df = new DecimalFormat("###.#");
        return Double.parseDouble(df.format(fleschScore));
    }
}
