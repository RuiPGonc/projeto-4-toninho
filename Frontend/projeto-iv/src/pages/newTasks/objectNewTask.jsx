import moment from "moment";
import getCurrentDateTime from "../../components/generics/systemDateTime";
import dateTime_format from "../../components/generics/dateTimeFormated";


export default function setObject_to_Fetch(
  title_Detail,
  categoryId,
  times,
  alert,
  done
) {
    
  const task = {
    title: title_Detail.title,
    categoryId: categoryId.categoryId,
    details: title_Detail.details,
    alert: alert,
    done: done,
    deadline: times.deadlineTime,
    startTime: times.startTime,
    timeReminder: times.timeReminder,
  };
  console.log(task);

  //validação de valores do tipo "undefined"
  if (typeof task.details === "undefined") {
    task.details = null;
  }
  if (typeof task.deadline === "undefined") {
    task.deadline = null;
  }
  if (typeof task.startTime === "undefined") {
    task.startTime = dateTime_format(getCurrentDateTime());

  }
  if (typeof task.timeReminder === "undefined") {
    task.timeReminder = null;
  }
  console.log(task);

  //validações de dados
  const start_Time = moment(times.startTime);
  const deadline_ = moment(times.deadline);
  const time_Reminder = moment(times.timeReminder);

  if (deadline_.isBefore(start_Time)) {
    console.log("deadline definido para uma data anterior à data de inicio");
    task = {};
  } else if (time_Reminder.isAfter(deadline_)) {
    console.log(
      "ReminderTime definido para uma data posterior à data deadline"
    );
    task = {};
  } else if (time_Reminder.isBefore(start_Time)) {
    console.log(
      "ReminderTime definido para uma data anterior à data de inicio"
    );
    task = {};
  }

  return task;
}
