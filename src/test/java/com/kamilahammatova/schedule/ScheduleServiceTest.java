//package com.kamilahammatova.schedule;
//
//import com.kamilahammatova.schedule.entities.mongodb.Audience;
//import com.kamilahammatova.schedule.entities.mongodb.Lesson;
//import com.kamilahammatova.schedule.entities.mongodb.Schedule;
//import com.kamilahammatova.schedule.entities.mongodb.Student;
//import com.kamilahammatova.schedule.entities.mongodb.Teacher;
//import com.kamilahammatova.schedule.entities.sbeans.DateRange;
//import com.kamilahammatova.schedule.entities.sbeans.Equipment;
//import com.kamilahammatova.schedule.repository.*;
//import com.kamilahammatova.schedule.service.ScheduleService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyCollection;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ScheduleServiceTest {
//
//  private LocalDateTime localDateTime = LocalDateTime.now();
//  private DateRange dateRange = new DateRange(localDateTime, localDateTime.plusMinutes(90));
//
//  @Mock
//  private LessonRepository lessonRepository;
//  @Mock
//  private AudienceRepository audienceRepository;
//  @Mock
//  private TeacherRepository teacherRepository;
//  @Mock
//  private StudentRepository studentRepository;
//  @Mock
//  private ScheduleRepository scheduleRepository;
//  @InjectMocks
//  private ScheduleService scheduleService;
//
//  @Test
//  void testGenerateSchedule() {
//    List<Lesson> mockLessons = new ArrayList<>();
//    Lesson lesson1 = new Lesson();
//    lesson1.setPrimaryId("1");
//    lesson1.setDateRange(new DateRange(LocalDateTime.of(2023, 6, 5, 9, 0), LocalDateTime.of(2023, 6, 5, 11, 0)));
//    lesson1.setTeacherIds(List.of("1"));
//    mockLessons.add(lesson1);
//
//    List<Audience> mockAudiences = new ArrayList<>();
//    Audience audience1 = new Audience();
//    audience1.setPrimaryId("1");
//    audience1.setRoomNumber(101);
//    audience1.setCapacity(30);
//    audience1.setAddress("123");
//    mockAudiences.add(audience1);
//
//    List<Teacher> mockTeachers = new ArrayList<>();
//    Teacher teacher1 = new Teacher();
//    teacher1.setPrimaryId("1");
//    teacher1.setFio("Name SecondName");
//    teacher1.setLessonIds(Set.of("1"));
//    teacher1.setPreferDateRange(Set.of(new DateRange(LocalDateTime.of(2023, 6, 5, 12, 0), LocalDateTime.of(2023, 6, 5, 14, 0))));
//    mockTeachers.add(teacher1);
//
//    Student student1 = new Student();
//    student1.setPrimaryId("1");
//    student1.setLessonIds(Set.of("1"));
//
//    when(lessonRepository.findAllById(anyCollection())).thenReturn(mockLessons);
//    when(audienceRepository.findAll()).thenReturn(mockAudiences);
//    when(teacherRepository.findAllByLessonIds(anyCollection())).thenReturn(mockTeachers);
//    when(studentRepository.findById(anyString())).thenReturn(Optional.of(student1));
//
//    List<Schedule> schedules = scheduleService.generateSchedule("1");
//
//    assertEquals(1, schedules.size());
//    Schedule schedule = schedules.get(0);
//    assertEquals("1", schedule.getLessonId());
//    assertEquals("1", schedule.getStudentId());
//    assertEquals("1", schedule.getAudienceId());
//    assertEquals("1", schedule.getTeacherId());
//    assertEquals(new DateRange(LocalDateTime.of(2023, 6, 5, 9, 0), LocalDateTime.of(2023, 6, 5, 11, 0)), schedule.getDateRange());
//    schedules.forEach(System.out::println);
//  }
//
//  @Test
//  void test2() {
//    List<Student> students = createStudents();
//    List<Lesson> lessons = createLessons();
//    List<Teacher> teachers = createTeachers();
//    List<Audience> audiences = createAudiences();
//
//    List<String> list = lessons.stream().map(Lesson::getPrimaryId).toList();
//    Random random = new Random();
//    teachers.forEach(x -> x.setLessonIds(Set.of(list.get(random.nextInt(4)))));
//
//    Map<String, Lesson> lessonMap = lessons.stream().collect(Collectors.toMap(Lesson::getPrimaryId, Function.identity()));
//
//    when(teacherRepository.findAllByLessonIds(anyCollection())).thenReturn(teachers);
//    Map<String, List<Schedule>> schedules1 = new HashMap<>();
//    when(audienceRepository.findAll()).thenReturn(audiences);
//    for (Student student : students) {
//      List<Lesson> collect = student.getLessonIds().stream().map(lessonMap::get).collect(Collectors.toList());
//      when(lessonRepository.findAllById(anyCollection())).thenReturn(collect);
//      when(studentRepository.findById(anyString())).thenReturn(Optional.of(student));
//      List<Schedule> schedules = scheduleService.generateSchedule(student.getPrimaryId());
//      schedules1.put(student.getPrimaryId(), schedules);
//    }
//    schedules1.forEach((k, v) -> System.out.println(k + " : " + v));
//  }
//
//  private List<Student> createStudents() {
//    List<String> lessonIds = createLessons().stream()
//        .map(Lesson::getPrimaryId)
//        .toList();
//    Random random = new Random();
//
//    return IntStream.range(1, 51).mapToObj(x -> {
//      Student student = new Student();
//      student.setFio("fio" + x);
//      student.setPrimaryId("student" + x);
//      student.setLessonIds(Set.of(lessonIds.get(random.nextInt(5))));
//      return student;
//    }).collect(Collectors.toList());
//  }
//
//  private List<Lesson> createLessons() {
//    List<String> teacherId = createTeachers().stream()
//        .map(Teacher::getPrimaryId)
//        .toList();
//    Random random = new Random();
//
//
//    return IntStream.range(1, 7).mapToObj(x -> {
//      Lesson lesson = new Lesson();
//      lesson.setPrimaryId("lesson" + x);
//      lesson.setDisplayName("ds" + x);
//      lesson.setDateRange(dateRange);
//      lesson.setTeacherIds(List.of(teacherId.get(random.nextInt(4))));
//      if (x % 2 == 0) {
//        lesson.setRequirements(Set.of(Equipment.TRIBUNE, Equipment.LAB));
//      } else {
//        lesson.setRequirements(Set.of(Equipment.TRIBUNE));
//      }
//      return lesson;
//    }).collect(Collectors.toList());
//  }
//
//  private List<Teacher> createTeachers() {
//    return IntStream.range(1, 5).mapToObj(x -> {
//      Teacher teacher = new Teacher();
//      teacher.setPrimaryId("teacher" + x);
//      teacher.setPreferStudentsCount(23);
//      teacher.setPreferDateRange(Set.of(dateRange));
//      return teacher;
//    }).collect(Collectors.toList());
//  }
//
//  private List<Audience> createAudiences() {
//    return IntStream.range(1, 5).mapToObj(x -> {
//      Audience audience = new Audience();
//      audience.setCapacity(25 + x);
//      audience.setRoomNumber(x);
//      audience.setPrimaryId("aud" + x);
//      if (x % 2 == 0) {
//        audience.setEquipments(Set.of(Equipment.TRIBUNE, Equipment.LAB));
//      } else {
//        audience.setEquipments(Set.of(Equipment.TRIBUNE));
//      }
//      return audience;
//    }).collect(Collectors.toList());
//  }
//}
