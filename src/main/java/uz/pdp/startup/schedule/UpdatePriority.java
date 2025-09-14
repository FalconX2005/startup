package uz.pdp.startup.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.pdp.startup.entity.Debts;
import uz.pdp.startup.enums.Priority;
import uz.pdp.startup.repository.DebtsRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class UpdatePriority {

    private final DebtsRepository  debtsRepository;

    @Scheduled(fixedRate = 1 , timeUnit = TimeUnit.DAYS)
    public void updatePriority(){
        List<Debts> all = debtsRepository.findAll();
//        if (all.isEmpty()){
//            throw RestException.error("list is empty");
//        }
        for(Debts debts: all){
          if (debts.getUpdated().equals(LocalDateTime.now()) && debts.getToDate().isAfter(LocalDate.now())){
              debts.setPriority(Priority.Y);
          }
          else {
              debts.setPriority(Priority.Z);
          }
        }
    }
}
