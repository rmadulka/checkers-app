package com.webcheckers.model;

/**
 * Represents a piece on a checkerboard. returns a color and a type.
 */
public class Piece {

    /**
     * Enums that define the two types of pieces and colors.
     */

    public enum pieceType {
        SINGLE,
        KING
    }

    public enum pieceColor {
        WHITE,
        RED
    }

    private pieceType type;
    private pieceColor color;

    /**
     * Constructor for a checkers piece.
     * @param type The type of piece constructed.
     * @param color the color of the piece constructed.
     */
    public Piece(pieceType type, pieceColor color) {
        this.type = type;
        this.color = color;
    }

    /**
     * What type of piece is it?
     * @return PieceType
     */
    public pieceType getType() {
        return this.type;
    }

    /**
     * Converts the given piece to a KING type
     * @param piece: given piece moved to the last row
     */
    public void convertToKing(Piece piece){
        piece.type = pieceType.KING;
    }

    /**
     * What color piece is this?
     * @return PieceColor
     */
    public pieceColor getColor() {
        return this.color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Piece) {
            return (this.type == ((Piece) obj).type &&
                    this.color == ((Piece) obj).color);
        }
        return false;
    }
}
