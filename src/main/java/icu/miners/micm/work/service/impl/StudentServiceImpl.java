package icu.miners.micm.work.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import icu.miners.micm.work.model.entity.Organization;
import icu.miners.micm.work.model.entity.Student;
import icu.miners.micm.work.repository.StudentRepository;
import icu.miners.micm.work.service.StudentService;
import icu.miners.micm.work.service.base.AbstractCrudService;
import icu.miners.micm.work.utils.TokenUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * xx
 * <p>
 * Description:
 * </p>
 *
 * @author: https://github.com/isColt
 * @date: 2020/9/15
 * @see: icu.miners.carte_alimentaire.service.impl
 * @version: v1.0.0
 */
@Service
public class StudentServiceImpl extends AbstractCrudService<Student, Integer> implements StudentService {

    @Resource
    private StudentRepository studentRepository;

    protected StudentServiceImpl(JpaRepository<Student, Integer> repository) {
        super(repository);
    }

    @Override
    public Student getByNumber(String number) {
        return studentRepository.findByNumber(number);
    }

    @Override
    public Boolean checkRole(String number) {
        Student student = getByNumber(number);
        if (student == null) {
            return false;
        }
        return student.getRole() == 1;
    }

    @Override
    public String getToken(Student student) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000;//7天有效时间
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(student.getId().toString()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(student.getPassword()));

        return token;
    }

    @Override
    public Student getCurrentUser() {
        if (TokenUtil.getTokenUserId() == null) {
            return null;
        }
        return getById(Integer.valueOf(TokenUtil.getTokenUserId()));
    }

    @Override
    public List<Student> listValid() {
        return studentRepository.findByDeletedIs(false);
    }

    @Override
    public List<Student> listAllByOrganization(Organization organization) {
        return studentRepository.findAllByOrganization(organization);
    }

    @Override
    public Student getByEmail(String email) {
        return studentRepository.findFirstByEmail(email);
    }
}
