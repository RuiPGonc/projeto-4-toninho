import React, {useState} from 'react';
import getCurrentDateTime from "../../components/generics/systemDateTime";
import moment from 'react'

export default function setObject_to_Fetch(title_Detail,categoryId,times,alert,done){
//const [systemTime, setStartTime] = useState(getCurrentDateTime());

//se a tarefa foi concluida envia-se a data do sistema como finishTime -> backend
/*if(done){
    const finishTime=systemTime
    done={
        ...done,
        ...finishTime
    }
}*/

    const task={
             ...title_Detail, 
             ...categoryId,
             ...times,
             ...alert,
             ...done
      };

//validações de dados
const start_Time= moment(times.startTime);
const deadline_= moment(times.deadline);
const time_Reminder= moment(times.timeReminder);

if(deadline_.isBefore(start_Time)){
   console.log("deadline definido para uma data anterior à data de inicio")
   task=[""];
}else if(time_Reminder.isAfter(deadline_)){
    console.log("ReminterTime definido para uma data posterior à data deadline")
    task=[""];
}else if(time_Reminder.isBefore(start_Time)){
    console.log("ReminterTime definido para uma data anterior à data de inicio")
    task=[""];
}

return task
}