package util.constants;

/**
 * enum contains pages to forward
 */
public enum ForwardPagesPaths {
    BOOKING_IS_CREATED("jsp/booking.jsp"), CLIENT_APPLICATIONS_AND_BOOKINGS("jsp/client-request.jsp"),
    ALL_APPLICATIONS("jsp/admin.jsp"), ALL_BOOKINGS("jsp/bookings.jsp"), MAIN_PAGE("jsp/home.jsp"),
    FORM_CLIENT_EMAIL_TEL("jsp/client-id.jsp"), FORM_CLIENT_INF("jsp/requestStep1.jsp"),
    FORM_CLIENT_ROOM("jsp/requestStep2.jsp"), DATA_NOT_SENT("jsp/data-not-sent.jsp"),
    THANKS_PAGE("jsp/thanks-page.jsp"), WRONG_DATA("jsp/data-not-sent.jsp"), ROOMS("jsp/admin-cards.jsp"),
    ERROR("jsp/data-not-sent.jsp");
    /**
     * page's path field
     */
    private String path;

    /**
     * constructor sets the path to a page name constant
     * @param path
     */
    ForwardPagesPaths(String path) {
        this.path = path;
    }

    /**
     * @return path field value
     */
    @Override
    public String toString() {
        return this.path;
    }
}
