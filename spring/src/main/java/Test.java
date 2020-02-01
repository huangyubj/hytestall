import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Test {
    public static void main(String[] args) {
        BeanPostProcessor  b = null;
        Aware aware = null;
        EnvironmentAware ea = null;
        ModelAndView modelAndView = null;
        Model model = null;
        ModelMap modelMap = null;
        HandlerInterceptor handlerInterceptor = null;
    }
}
