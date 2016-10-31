package DocumentClasses;

import java.util.Map;

/**
 * Created by Ryan on 9/27/2016.
 */
public interface DocumentDistance {
    double findDistance(TextVector query, TextVector document, DocumentCollection documents, int docNum);
}
