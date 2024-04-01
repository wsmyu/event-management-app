export const formatDate = (dateArray) => {
    const year = dateArray[0];
    const month = dateArray[1].toString().padStart(2, '0');
    const day = dateArray[2].toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
};

export const formatTime = (timeArray) => {
    const hours = timeArray[0].toString().padStart(2, '0');
    const minutes = timeArray[1].toString().padStart(2, '0');
    return `${hours}:${minutes}`;
};

export const fetchVenueDetails = async (venueId) => {
    if (!venueId) {
        // Handle the case where venueId is null or undefined
        console.error('Venue ID is null or undefined');
        return null;
    }
    try {
        const venueResponse = await fetch(`http://localhost:8080/api/venues/${venueId}`);
        if (venueResponse.ok) {
            return await venueResponse.json();
        } else {
            throw new Error('Failed to fetch venue details');
        }
    } catch (error) {
        console.error(error);
        throw error;
    }
};