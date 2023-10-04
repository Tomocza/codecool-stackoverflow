function DateFormatter({date}) {
    function formatDate(date) {
        let formattedDate = "";
        let parsedDate = new Date(...date);
        formattedDate += 1900 + +parsedDate.getYear();
        formattedDate += "-" + extendToTwoDigits(parsedDate.getMonth() - 1);
        formattedDate += "-" + extendToTwoDigits(parsedDate.getDate());
        formattedDate += " " + extendToTwoDigits(parsedDate.getHours());
        formattedDate += ":" + extendToTwoDigits(parsedDate.getMinutes());
        formattedDate += ":" + extendToTwoDigits(parsedDate.getSeconds());


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