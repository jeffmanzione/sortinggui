package sorts.business;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@Retention(RetentionPolicy.RUNTIME)
public @interface SortInfo {
	String name();
	String designer();
	String link();
}
