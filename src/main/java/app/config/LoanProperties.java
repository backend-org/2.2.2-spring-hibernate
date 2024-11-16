package app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "loan-properties")
@Getter
@Setter
public class LoanProperties {
    int minCarCostForLoanApprove;
    int minUserIncomeForLoanApprove;
    double maxApproveMultiplierOfCarCost;
    int maxApproveMultiplierOfCountOfMonthsIncome;
    String usersIncomeUri;
}
