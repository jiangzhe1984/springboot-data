package sanjiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sanjiang.domain.Personnel;
import sanjiang.repository.PersonnelRepository;

import java.util.List;

/**
 * Created by jiangzhe on 15-11-10.
 */
@Service
public class PersonnelService extends BaseService<Personnel> {
    @Autowired
    private PersonnelRepository personnelRepository;

    public Personnel save(Personnel personnel){
       return  personnelRepository.save(personnel);
    }

    public Personnel findById(Long id){
        return personnelRepository.findOne(id);
    }

    public List<Personnel> findAll(){
        return personnelRepository.findAll();
    }


}
