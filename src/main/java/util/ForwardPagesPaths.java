package util;

public enum ForwardPagesPaths {
    BOOKING_IS_CREATED("/booking.jsp"), CLIENT_APPLICATIONS_AND_BOOKINGS("/client-request.jsp"),
    ALL_APPLICATIONS("/admin.jsp"), ALL_BOOKINGS("/bookings.jsp"), MAIN_PAGE("/home.jsp"),
    FORM_CLIENT_EMAIL_TEL("/client-id.jsp"), FORM_CLIENT_INF("/requestStep1.jsp"),
    FORM_CLIENT_ROOM("/requestStep2.jsp"), DATA_NOT_SENT("/data-not-sent.jsp"),
    THANKS_PAGE("/thanks-page.jsp"), WRONG_DATA("/data-not-sent.jsp"), ROOMS("/admin-cards.jsp"),
    ERROR("/data-not-sent.jsp");

    private String path;

    ForwardPagesPaths(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
