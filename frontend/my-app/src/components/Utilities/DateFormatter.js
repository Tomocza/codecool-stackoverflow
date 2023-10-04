function DateFormatter({ date }) {
    function formatDate() {
        let formattedDate = "";
        if (Array.isArray(date)) {
            formattedDate += extendToTwoDigits(date[0]);
            formattedDate += "-" + extendToTwoDigits(date[1]);
            formattedDate += "-" + extendToTwoDigits(date[2]);
            formattedDate += " " + extendToTwoDigits(date[3]);
            formattedDate += ":" + extendToTwoDigits(date[4]);
            formattedDate += ":" + extendToTwoDigits(date[5]);

        } else {
            let parsedDate = new Date(date);
            formattedDate += 1900 + +parsedDate.getYear();
            formattedDate += "-" + extendToTwoDigits(parsedDate.getMonth());
            formattedDate += "-" + extendToTwoDigits(parsedDate.getDate());
            formattedDate += " " + extendToTwoDigits(parsedDate.getHours());
            formattedDate += ":" + extendToTwoDigits(parsedDate.getMinutes());
            formattedDate += ":" + extendToTwoDigits(parsedDate.getSeconds());
        }
        return formattedDate;
    }

    function extendToTwoDigits(datePart) {
        let extendedDatePart = "";
        if (datePart.toString().length === 1) {
            extendedDatePart += "0" + datePart;
        } else {
            extendedDatePart += datePart;
        }
        return extendedDatePart;
    }
    return (
        <>
            {formatDate(date ?? [0, 0, 0, 0, 0, 0])}
        </>
    )
}

export default DateFormatter;