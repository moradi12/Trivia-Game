export interface Question {
    id: number;
    text: string;
    options: string[];
    correctIndex: number;
    category: string;     // Serialized enums become strings
    difficulty: string;
}
