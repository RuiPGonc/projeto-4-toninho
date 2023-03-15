import React, { useState } from "react";


export default function getCurrentDateTime() {
    

        const now = new Date();
        const year = now.getFullYear();
        const month = now.getMonth() + 1;
        const day = now.getDate();
        const hours = now.getHours();
        const minutes = now.getMinutes();
        const paddedMonth = month.toString().padStart(2, "0");
        const paddedDay = day.toString().padStart(2, "0");
        const paddedHours = hours.toString().padStart(2, "0");
        const paddedMinutes = minutes.toString().padStart(2, "0");
        
        
        return (`${year}-${paddedMonth}-${paddedDay}T${paddedHours}:${paddedMinutes}`);
}
